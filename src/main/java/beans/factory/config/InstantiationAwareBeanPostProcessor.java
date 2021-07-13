package beans.factory.config;

import beans.BeansException;
//实例化
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName)
            throws BeansException;
}
