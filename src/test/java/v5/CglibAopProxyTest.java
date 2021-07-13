package v5;

import aop.aspectj.AspectJAfterReturningAdvice;
import aop.aspectj.AspectJBeforeAdvice;
import aop.aspectj.AspectJExpressionPointcut;
import aop.config.AspectInstanceFactory;
import aop.framework.AopConfig;
import aop.framework.AopConfigSupport;
import aop.framework.CglibProxyFactory;
import beans.factory.BeanFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.v5.PetStoreService;
import tx.TransactionManager;
import util.MessageTracker;

import java.util.List;

public class CglibAopProxyTest extends AbstractV5Test{
    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterAdvice = null;
    private static AspectJExpressionPointcut pc = null;
    private BeanFactory beanFactory = null;
    private AspectInstanceFactory aspectInstanceFactory = null;

    private TransactionManager tx;

    @Before
    public  void setUp() throws Exception{
        MessageTracker.clearMsgs();

        tx = new TransactionManager();
        String expression = "execution(* service.v5.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        beanFactory = this.getBeanFactory("petstore_v5.xml");//读取配置文件
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");//获取tx实例对象
        aspectInstanceFactory.setBeanFactory(beanFactory);

        beforeAdvice = new AspectJBeforeAdvice(
                getAdviceMethod("start"),
                pc,
                aspectInstanceFactory);

        afterAdvice = new AspectJAfterReturningAdvice(
                getAdviceMethod("commit"),
                pc,
                aspectInstanceFactory);

    }

    @Test
    public void testGetProxy(){

        AopConfig config = new AopConfigSupport();

        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(new PetStoreService());

        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        PetStoreService proxy = (PetStoreService)proxyFactory.getProxy();

        proxy.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

        proxy.toString();
    }


}
