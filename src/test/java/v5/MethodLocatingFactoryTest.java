package v5;

import aop.config.MethodLocatingFactory;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import core.io.Resource;
import org.junit.Assert;
import org.junit.Test;
import tx.TransactionManager;

import java.lang.reflect.Method;

//匹配方法已经在Pointcut中搞掂，现在需要定位xml配置中的方法（通过beanid，方法名）
//所以需要依赖beanID在beanfactory中创建bean类
public class MethodLocatingFactoryTest {
    @Test
    public void testGetMethod() throws Exception{
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("petstore_v5.xml");
        reader.loadBeanDefinitions(resource);

        MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
        methodLocatingFactory.setTargetBeanName("tx");
        methodLocatingFactory.setMethodName("start");
        methodLocatingFactory.setBeanFactory(beanFactory);

        Method m = methodLocatingFactory.getObject();
        //判断类是否匹配，方法是否匹配
        Assert.assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
        Assert.assertTrue(m.equals(TransactionManager.class.getMethod("start")));
    }
}
