package beans.factory.support;

import beans.BeanDefinition;
import beans.ConstructorArgument;
import beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

        private String id;
        private String beanClassName;
        private Class<?> beanClass;


        private boolean singleton = true;
        private boolean prototype = false;
        private String scope = SCOPE_DEFAULT;
        List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

        private ConstructorArgument constructorArgument = new ConstructorArgument();

        //表明这个Bean定义是不是我们litespring自己合成的。
        private boolean isSynthetic = false;

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
    public GenericBeanDefinition(String id, String beanClassName) {

            this.id = id;
            this.beanClassName = beanClassName;
    }
    public GenericBeanDefinition(Class<?> clz) {
        this.beanClass = clz;
        this.beanClassName = clz.getName();
    }
    public GenericBeanDefinition() {

    }

    public String getBeanClassName() {

            return this.beanClassName;
    }

    @Override
    public boolean isSingleton() {
            return this.singleton; 
        }
    @Override
    public boolean isPrototype() {
            return this.prototype;
        }
    @Override
    public String getScope() {
            return this.scope;
        }



    @Override 
        public  void setScope(String scope) {
            this.scope = scope;
            this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
            this.prototype = SCOPE_PROTOTYPE.equals(scope);
        }
    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    @Override
    public String getID() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgument.isEmpty();
    }

    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        //这里创建一个beanclass
        String className = getBeanClassName();//获取当前bean的名字
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = classLoader.loadClass(className);//通过名字加载类
        this.beanClass = resolvedClass;
        return resolvedClass;

    }

    @Override
    public Class<?> getBeanClass() throws IllegalStateException {
        if(this.beanClass == null){
            throw new IllegalStateException(
                    "Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
        }
        return this.beanClass;
    }

    @Override
    public boolean hasBeanClass() {
        return this.beanClass != null;
    }

    @Override
    public boolean isSynthetic() {
            return isSynthetic;
    }
    public void setSynthetic(boolean isSynthetic) {
        this.isSynthetic = isSynthetic;
    }

}
