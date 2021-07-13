package aop.aspectj;

import aop.Advice;
import aop.MethodMatcher;
import aop.Pointcut;
import aop.framework.AopConfigSupport;
import aop.framework.AopProxyFactory;
import aop.framework.CglibProxyFactory;
import aop.framework.JdkAopProxyFactory;
import beans.BeansException;
import beans.factory.config.BeanPostProcessor;
import beans.factory.config.ConfigurableBeanFactory;
import util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//自动生成代理，在 AbstractApplicationContext中注册 调用
public class AspectJAutoProxyCreator implements BeanPostProcessor {
    ConfigurableBeanFactory beanFactory;
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object afterInitialization(Object bean, String beanName) throws BeansException {

        //如果这个Bean本身就是Advice及其子类，那就不要再生成动态代理了。
        if(isInfrastructureClass(bean.getClass())){
            return bean;
        }

        List<Advice> advices = getCandidateAdvices(bean);
        if(advices.isEmpty()){
            return bean;
        }

        return createProxy(advices,bean);
    }

    private List<Advice> getCandidateAdvices(Object bean){
        //获取bean候选的advice
        List<Object> advices = this.beanFactory.getBeansByType(Advice.class);

        List<Advice> result = new ArrayList<Advice>();
        for(Object o : advices){
            Pointcut pc = ((Advice) o).getPointcut();
            if(canApply(pc,bean.getClass())){//判断pointcut是否能对该bean处理
                result.add((Advice) o);
            }

        }
        return result;
    }

    protected Object createProxy( List<Advice> advices ,Object bean) {

        //遍历所有的advice加入配置类中
        AopConfigSupport config = new AopConfigSupport();
        for(Advice advice : advices){
            config.addAdvice(advice);
        }

        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        for (Class<?> targetInterface : targetInterfaces) {
            config.addInterface(targetInterface);
        }

        config.setTargetObject(bean);

        AopProxyFactory proxyFactory = null;
        //生成代理工厂
        if(config.getProxiedInterfaces().length == 0){
            proxyFactory =  new CglibProxyFactory(config);
        } else{
            //TODO 需要实现JDK 代理
            proxyFactory = new JdkAopProxyFactory(config);//创建jdk代理
        }

//FIXME
        return proxyFactory.getProxy();//获取代理


    }

    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass);

        return retVal;
    }

    public void setBeanFactory(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;

    }

    public static boolean canApply(Pointcut pc, Class<?> targetClass) {


        MethodMatcher methodMatcher = pc.getMethodMatcher();

        Set<Class> classes = new LinkedHashSet<Class>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
        classes.add(targetClass);
        //查看方法是否匹配
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (methodMatcher.matches(method/*, targetClass*/)) {
                    return true;
                }
            }
        }

        return false;
    }

}

