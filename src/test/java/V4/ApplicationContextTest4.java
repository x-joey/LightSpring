package V4;

import context.ApplicationContext;
import context.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import service.v4.PetStoreService;

public class ApplicationContextTest4 {
    @Test
    public void testGetBeanProperty(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore_v4.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }
}
