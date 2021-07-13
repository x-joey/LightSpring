package v5;

import aop.MethodMatcher;
import aop.aspectj.AspectJExpressionPointcut;
import org.junit.Assert;
import org.junit.Test;
import service.v5.PetStoreService;

import java.lang.reflect.Method;

//解析字符串表达式为对象，判断对象match方法是否成功
public class PointcutTest {
    @Test
    public void testPointcut() throws Exception{
        String expression = "execution(* service.v5.*.placeOrder(..))";
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);//获取表达式

        MethodMatcher mm = pc.getMethodMatcher();//发射得到表达式的方法对象

        //传入方法对象，看与表达式中的是否匹配
        {
            Class<?> targetClass = PetStoreService.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method2));
        }

        {
            Class<?> targetClass =service.v4.PetStoreService.class;

            Method method = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method));
        }


    }
}
