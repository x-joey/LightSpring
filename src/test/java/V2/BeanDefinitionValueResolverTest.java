package V2;

import beans.factory.config.RuntimeBeanReference;
import beans.factory.config.TypedStringValue;
import beans.factory.support.BeanDefinitionValueResolver;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import dao.v2.AccountDao;
import org.junit.Assert;
import org.junit.Test;
//对成员变量是bean对象的情况进行处理
public class BeanDefinitionValueResolverTest {
    @Test
    public void testResolveRuntimeBeanReference(){
        //不通过applicationContext获取工厂
        DefaultBeanFactory factory = new DefaultBeanFactory();
        //解析器对象
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        //解析xml，调用类处理资源
        reader.loadBeanDefinitions(new ClassPathResource("petstore_v2.xml"));
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);
        Assert.assertNotNull(value);
        Assert.assertNotNull(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue(){
        //不通过applicationContext获取工厂
        DefaultBeanFactory factory = new DefaultBeanFactory();
        //解析器对象
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        //解析xml，调用类处理资源
        reader.loadBeanDefinitions(new ClassPathResource("petstore_v2.xml"));
        //bean定义值解析器，解析String类型
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        TypedStringValue stringValue = new TypedStringValue("test");

        Object value = resolver.resolveValueIfNecessary(stringValue);

        Assert.assertNotNull(value);
        Assert.assertEquals("test",value);

    }
}
