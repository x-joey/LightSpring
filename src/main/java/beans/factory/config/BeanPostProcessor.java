package beans.factory.config;

import beans.BeansException;

public interface BeanPostProcessor {
    //初始化
    Object beforeInitialization(Object bean, String beanName) throws BeansException;


    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
