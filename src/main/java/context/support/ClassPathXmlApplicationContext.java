package context.support;

import beans.BeanDefinition;
import beans.factory.support.DefaultBeanFactory;
import context.ApplicationContext;
import core.io.ClassPathResource;
import core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);

    }

    @Override
    protected Resource getResourceByPath(String path) {

        return new ClassPathResource(path,this.getBeanClassLoader());
    }

}