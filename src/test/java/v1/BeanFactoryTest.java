package v1;

import beans.BeanDefinition;
import beans.factory.BeanCreationException;
import beans.factory.BeanDefinitionStoreException;
import beans.factory.BeanFactory;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.v1.PetStoreService;

import static org.junit.Assert.*;

public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
/*
        //给定一个配置文件，我们获取bean定义，而想获取bean定义，则需要BeanFactory
        BeanFactory factory = new DefaultBeanFactory("petstore_v1.xml");
        //获取bean工厂后，需要获取bean特定类定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        //断言看获取到的类定义是否和我们想要的相同
        assertEquals("service.v1.PetStoreService",bd.getBeanClassName());

 */
    }

    @Test
    public void testGetBean(){

        reader.loadBeanDefinitions(new ClassPathResource("petstore_v1.xml"));
        //获取bean工厂后，需要获取bean特定类定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        //断言看获取到的类定义是否和我们想要的相同
        assertEquals("service.v1.PetStoreService",bd.getBeanClassName());
        //获取bean工厂后，通过id获取特定的bean
        PetStoreService petSore = (PetStoreService) factory.getBean("petStore");

        assertNotNull(petSore);
    }
    @Test
    public void testInvalidBean(){

        reader.loadBeanDefinitions(new ClassPathResource("petstore_v1.xml"));
//        BeanFactory factory = new DefaultBeanFactory("petstore_v1.xml");
        try{
            factory.getBean("invalidBean");
        }catch(BeanCreationException e){
            return;
        }
        Assert.fail("expect BeanCreationException ");
    }
    @Test
    public void testInvalidXML(){

        try{
//            new DefaultBeanFactory("xxx.xml");
            reader.loadBeanDefinitions(new ClassPathResource("xxxx.xml"));
        }catch(  BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }

}

/*3
TDD 流程
1. 写失败的测试代码
2. 写测试代码
3. 测试通过

* */