package aop.config;

import beans.BeanUtils;
import beans.factory.BeanFactory;
import beans.factory.BeanFactoryAware;
import beans.factory.FactoryBean;
import util.StringUtils;

import java.lang.reflect.Method;
//定位方法所在的类和方法，通过反射获取该方法实例
public class MethodLocatingFactory implements FactoryBean<Method>, BeanFactoryAware {

    private String targetBeanName;

    private String methodName;

    private Method method;

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
//返回method
    public Method getObject() throws Exception {
        return this.method;
    }

    @Override
    public Class<?> getObjectType() {//返回class
        return Method.class;
    }

    public void setBeanFactory(BeanFactory beanFactory){
        if (!StringUtils.hasText(this.targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }
        if (!StringUtils.hasText(this.methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }
        //获取类字节码
        Class<?> beanClass = beanFactory.getType(this.targetBeanName);
        if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
        }
        //处理可能带括号的情况，反射获取方法
        //把beanclass中的method取出来
        this.method = BeanUtils.resolveSignature(this.methodName, beanClass);

        if (this.method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }

    }


}
