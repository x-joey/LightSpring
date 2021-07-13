package V3;

import beans.BeanDefinition;
import beans.ConstructorArgument;
import beans.factory.config.RuntimeBeanReference;
import beans.factory.config.TypedStringValue;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionTestV3 {
    @Test
    public void testConstructorArgument(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        //类加载器调用inpustream加载xml文件
        Resource resource = new ClassPathResource("petstore_v3.xml");
        reader.loadBeanDefinitions(resource);
        //获取bean定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertEquals("service.v3.PetStoreService",bd.getBeanClassName());

        ConstructorArgument args = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();
        Assert.assertEquals(3,valueHolders.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao",ref1.getBeanName());

        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao",ref2.getBeanName());

        TypedStringValue strValue = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1",strValue.getValue());


    }
}
