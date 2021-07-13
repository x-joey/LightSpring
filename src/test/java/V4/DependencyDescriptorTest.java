package V4;

import beans.factory.config.DependencyDescriptor;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import core.io.Resource;
import dao.v4.AccountDao;
import org.junit.Assert;
import org.junit.Test;
import service.v4.PetStoreService;

import java.lang.reflect.Field;

public class DependencyDescriptorTest {
    @Test
    public void testResolveDependency() throws Exception{
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore_v4.xml");
        reader.loadBeanDefinitions(resource);
        Field f = PetStoreService.class.getDeclaredField("accountDao");//反射获取PetStoreService的成员变量
        //将该字段进行描述
        DependencyDescriptor descriptor = new DependencyDescriptor(f,true);
        //根据Class类型从BeanFactory获取对象
        Object o = factory.resolveDependency(descriptor);//解决依赖，生成对象
        Assert.assertTrue(o instanceof AccountDao);


    }
}
