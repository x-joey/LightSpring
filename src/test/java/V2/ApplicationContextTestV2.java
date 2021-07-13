package V2;

import context.ApplicationContext;
import context.support.ClassPathXmlApplicationContext;
import static  org.junit.Assert.*;

import dao.v2.AccountDao;
import dao.v2.ItemDao;
import org.junit.Test;
import service.v2.PetStoreService;

import static junit.framework.TestCase.assertTrue;

public class ApplicationContextTestV2 {
    @Test
    public void testGetBeanProperty(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore_v2.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);

        assertEquals("xujunwei",petStore.getOwner());
        assertEquals(2,petStore.getVersion());

    }
}
