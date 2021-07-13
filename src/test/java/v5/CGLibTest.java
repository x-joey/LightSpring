package v5;

import org.junit.Test;
import org.springframework.cglib.proxy.*;
import service.v5.PetStoreService;
import tx.TransactionManager;

import java.lang.reflect.Method;

public class CGLibTest {

    @Test
    public void testCallBack(){

        Enhancer enhancer = new Enhancer();
        //增强者设置父类
        enhancer.setSuperclass(PetStoreService.class);
        //增强过程需要运行的方法
        enhancer.setCallback( new TransactionInterceptor() );
        //生成PetStoreService的动态代理类
        PetStoreService petStore = (PetStoreService)enhancer.create();
        //对placeOrder方法增强
        petStore.placeOrder();


    }
//回调，执行流程
    public static class TransactionInterceptor implements MethodInterceptor {
        TransactionManager txManager = new TransactionManager();
        //匿名类，安排执行顺序
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            txManager.start();
            Object result = proxy.invokeSuper(obj, args);
            txManager.commit();

            return result;
        }
    }

    @Test
    public void  testFilter(){

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);

        enhancer.setInterceptDuringConstruction(false);

        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};

        //拦截器类型有多少种
        Class<?>[] types = new Class<?>[callbacks.length];
        for (int x = 0; x < types.length; x++) {
            types[x] = callbacks[x].getClass();
        }


        //过滤器
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);


        PetStoreService petStore = (PetStoreService)enhancer.create();
        petStore.placeOrder();
        System.out.println(petStore.toString());

    }
    //回调代理过滤器，当有多个需要增强的方法时，判断执行需要增强的方法
    private static class ProxyCallbackFilter implements CallbackFilter {

        public ProxyCallbackFilter() {

        }

        public int accept(Method method) {
            if(method.getName().startsWith("place")){
                return 0;
            } else{
                return 1;
            }

        }

    }

}
