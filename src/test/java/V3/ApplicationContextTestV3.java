package V3;

import context.ApplicationContext;
import context.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import service.v3.PetStoreService;

public class ApplicationContextTestV3 {
    @Test
    public void testGetBeanProperty(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore_v3.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertEquals(1,petStore.getVersion());
    }
}
