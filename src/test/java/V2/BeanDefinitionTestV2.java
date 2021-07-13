package V2;

import beans.BeanDefinition;
import beans.PropertyValue;
import beans.factory.config.RuntimeBeanReference;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
//由于xml文件中bean内容中增加了属性值，所以beanDefinition类需要重新定义，而这里是将属性抽出作为另外一个类
public class BeanDefinitionTestV2 {
    @Test
    public void testGetBeanDefinition(){
        //不通过applicationContext获取工厂
        DefaultBeanFactory factory = new DefaultBeanFactory();
        //解析器对象
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        //解析xml，调用类处理资源
        reader.loadBeanDefinitions(new ClassPathResource("petstore_v2.xml"));

        //获取类定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        //PropertyValue类作为属性值类，细分粒度
        //getPropertyValues获取属性值，生成属性列表，
        List<PropertyValue> pvs = bd.getPropertyValues();

        Assert.assertTrue(pvs.size()==4);
        {
            PropertyValue pv = this.getPropertyValue("accountDao",pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

    }

    private PropertyValue getPropertyValue(String name,List<PropertyValue> pvs){
        for (PropertyValue pv :pvs) {
           if(pv.getName().equals(name)){
               return pv;
           }
        }
        return null;
    }
}
