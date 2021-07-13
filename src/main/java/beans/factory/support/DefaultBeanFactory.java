package beans.factory.support;

import beans.BeanDefinition;
import beans.BeansException;
import beans.PropertyValue;
import beans.SimpleTypeConverter;
import beans.factory.*;
import beans.factory.config.BeanPostProcessor;
import beans.factory.config.ConfigurableBeanFactory;
import beans.factory.config.DependencyDescriptor;
import beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory  extends AbstractBeanFactory
        implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader beanClassLoader;
    private static final Log logger = LogFactory.getLog(DefaultBeanFactory.class);
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    public DefaultBeanFactory() {

    }

    public void registerBeanDefinition(String beanID,BeanDefinition bd){
        this.beanDefinitionMap.put(beanID, bd);
    }
    public BeanDefinition getBeanDefinition(String beanID) {

        return this.beanDefinitionMap.get(beanID);
    }
    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if(bd == null){
            return null;
        }

        if(bd.isSingleton()){
            //单例
            Object bean = this.getSingleton(beanID);
            if(bean == null){
                bean = createBean(bd);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {

        BeanDefinition bd = this.getBeanDefinition(name);
        if(bd == null){
            throw new NoSuchBeanDefinitionException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }

    @Override
    public List<Object> getBeansByType(Class<?> type) {

        List<Object> result = new ArrayList<Object>();
        List<String> beanIDs = this.getBeanIDsByType(type);//字节码class对象获取beanID
        for(String beanID : beanIDs){
            result.add(this.getBean(beanID));//beanID创建获取bean
        }
        return result;
    }

    private List<String> getBeanIDsByType(Class<?> type){
        //字节码class对象获取beanID
        List<String> result = new ArrayList<String>();
        for(String beanName :this.beanDefinitionMap.keySet()){
            Class<?> beanClass = null;
            try{
                beanClass = this.getType(beanName);
            }catch(Exception e){
                logger.warn("can't load class for bean :"+beanName+", skip it.");
                continue;
            }
            if((beanClass!=null) && type.isAssignableFrom(beanClass)){
                result.add(beanName);
            }
        }
        return result;
    }

    protected Object createBean(BeanDefinition bd) {
        //创建实例
        Object bean = instantiateBean(bd);
        //设置属性
        populateBean(bd, bean);
        //初始化bean
        bean = initializeBean(bd,bean);

        return bean;

    }

    private Object instantiateBean(BeanDefinition bd) {
        if(bd.hasConstructorArgumentValues()){
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }else{
            //获取类加载器
            ClassLoader cl = this.getBeanClassLoader();
            //获取类名
            String beanClassName = bd.getBeanClassName();
            try {
                //反射加载类
                Class<?> clz = cl.loadClass(beanClassName);
                return clz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
            }
        }

    }
    //成员变量是bean
    protected void populateBean(BeanDefinition bd, Object bean){
        for(BeanPostProcessor processor : this.getBeanPostProcessors()){
            if(processor instanceof InstantiationAwareBeanPostProcessor){
                ((InstantiationAwareBeanPostProcessor)processor).postProcessPropertyValues(bean, bd.getID());
            }
        }
        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try{

            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);

                for (PropertyDescriptor pd : pds) {
                    if(pd.getName().equals(propertyName)){
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }


            }
        }catch(Exception ex){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }
    //初始化bean
    protected Object initializeBean(BeanDefinition bd, Object bean)  {
        invokeAwareMethods(bean);
        //Todo，调用Bean的init方法，暂不实现
        //创建代理，不是合成bean才做postprocessor
        if(!bd.isSynthetic()){
            return applyBeanPostProcessorsAfterInitialization(bean,bd.getID());
        }
        return bean;
    }
    private void invokeAwareMethods(final Object bean) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }
    //生命周期创建代理
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        //拿到所有的beanProcessor遍历
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.afterInitialization(result, beanName);//在AspectJAutoProxyCreator，创建代理
            if (result == null) {
                return result;
            }
        }
        return result;
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }
    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    //根据Class类型从BeanFactory获取对象
    //DependencyDescriptor描述了该类
    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for(BeanDefinition bd: this.beanDefinitionMap.values()){
            //确保BeanDefinition 有Class对象
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if(typeToMatch.isAssignableFrom(beanClass)){
                return this.getBean(bd.getID());//匹配的话通过beanID获取bean
            }
        }
        return null;
    }

    private void resolveBeanClass(BeanDefinition bd) {
        //判断有没有该BeanClass,如果有就返回，如果没有就创建
        if(bd.hasBeanClass()){
            return;
        } else{
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:"+bd.getBeanClassName());
            }
        }
    }
}
