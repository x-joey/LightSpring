package V3;

import beans.BeanDefinition;
import beans.factory.support.ConstructorResolver;
import beans.factory.support.DefaultBeanFactory;
import beans.factory.xml.XmlBeanDefinitionReader;
import core.io.ClassPathResource;
import core.io.Resource;
import org.junit.Assert;
import org.junit.Test;
import service.v3.PetStoreService;

public class ConstructorResolverTest {
    @Test
    public void testAutowireConstructor(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        //这个可以
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        Resource resource = new ClassPathResource("petstore_v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStore = (PetStoreService) resolver.autowireConstructor(bd);

        Assert.assertEquals(1,petStore.getVersion());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertNotNull(petStore.getAccountDao());
    }
}
