package v5;

import aop.Advice;
import aop.aspectj.AspectJAfterReturningAdvice;
import aop.aspectj.AspectJAfterThrowingAdvice;
import aop.aspectj.AspectJBeforeAdvice;
import beans.factory.BeanFactory;
import org.junit.Assert;
import org.junit.Test;
import tx.TransactionManager;

import java.util.List;
// BeanDefinitionTestV5中拿到从xml中读取到的advice生成的bean定义
// 接下来检验从factory中获取所有advice是否成功
public class BeanFactoryTestV5  extends AbstractV5Test {

    static String expectedExpression = "execution(* service.v5.*.placeOrder(..))";
    @Test
    public void testGetBeanByType() throws Exception{

        BeanFactory factory = super.getBeanFactory("petstore_v5.xml");

        List<Object> advices = factory.getBeansByType(Advice.class);

        Assert.assertEquals(3, advices.size());

        {
            AspectJBeforeAdvice advice = (AspectJBeforeAdvice)this.getAdvice(AspectJBeforeAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("start"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }


        {
            AspectJAfterReturningAdvice advice = (AspectJAfterReturningAdvice)this.getAdvice(AspectJAfterReturningAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("commit"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }

        {
            AspectJAfterThrowingAdvice advice = (AspectJAfterThrowingAdvice)this.getAdvice(AspectJAfterThrowingAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("rollback"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }


    }
//遍历匹配advice
    public Object getAdvice(Class<?> type,List<Object> advices){
        for(Object o : advices){
            if(o.getClass().equals(type)){
                return o;
            }
        }
        return null;
    }

}
