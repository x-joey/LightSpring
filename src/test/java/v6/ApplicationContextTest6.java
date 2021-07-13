package v6;

import context.ApplicationContext;
import context.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.v6.IPetStoreService;
import util.MessageTracker;

import java.util.List;

public class ApplicationContextTest6 {



    @Test
    public void testGetBeanProperty() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore_v6.xml");
        IPetStoreService petStore = (IPetStoreService)ctx.getBean("petStore");

        petStore.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();

        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

    }

    @Before
    public void setUp(){
        MessageTracker.clearMsgs();
    }
}
