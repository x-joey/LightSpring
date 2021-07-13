package v5;

import aop.config.AspectInstanceFactory;
import beans.factory.BeanFactory;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import core.io.Resource;
import tx.TransactionManager;

import java.lang.reflect.Method;

public class AbstractV5Test {
    protected BeanFactory getBeanFactory(String configFile){
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinitions(resource);
        return  defaultBeanFactory;
    }

    protected Method getAdviceMethod(String methodName) throws Exception{
        return TransactionManager.class.getMethod(methodName);
    }

    protected AspectInstanceFactory getAspectInstanceFactory(String targetBeanName){
        AspectInstanceFactory factory = new AspectInstanceFactory();
        factory.setAspectBeanName(targetBeanName);
        return factory;
    }
}
