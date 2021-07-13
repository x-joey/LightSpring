package beans.factory;

import beans.BeanDefinition;

import java.util.List;

public interface BeanFactory {
    //BeanDefinition getBeanDefinition(String beanId);
    Object getBean(String beanId);
    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    List<Object> getBeansByType(Class<?> type);
}
