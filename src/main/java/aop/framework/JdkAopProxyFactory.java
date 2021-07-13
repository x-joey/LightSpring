package aop.framework;

import aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

//InvocationHandler，实现jdk一定得传入handler
public class JdkAopProxyFactory implements AopProxyFactory, InvocationHandler {



    /** We use a static Log to avoid serialization issues */
    private static final Log logger = LogFactory.getLog(JdkAopProxyFactory.class);

    /** Config used to configure this proxy */
    private final AopConfig config;

    public JdkAopProxyFactory(AopConfig config) throws AopConfigException {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0 ) {
            throw new AopConfigException("No advices specified");
        }
        this.config = config;
    }


    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating JDK dynamic proxy: target source is " + this.config.getTargetObject());
        }
        Class<?>[] proxiedInterfaces = config.getProxiedInterfaces();//获取需要代理的接口

        return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);//创建实例
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object target = this.config.getTargetObject();

        Object retVal;


        // Get the interception chain for this method.
        List<Advice> chain = this.config.getAdvices(method);

        // Check whether we have any advice. If we don't, we can fallback on direct
        // reflective invocation of the target, and avoid creating a MethodInvocation.
        if (chain.isEmpty()) {
            // We can skip creating a MethodInvocation: just invoke the target directly
            // Note that the final invoker must be an InvokerInterceptor so we know it does
            // nothing but a reflective operation on the target, and no hot swapping or fancy proxying.
            retVal = method.invoke(target, args);
        }
        else {

            List<MethodInterceptor> interceptors = 	new ArrayList<MethodInterceptor>();

            interceptors.addAll(chain);


            // We need to create a method invocation...
            retVal = new ReflectiveMethodInvocation(target, method, args, interceptors).proceed();

        }

        // Massage return value if necessary.
		/*Class<?> returnType = method.getReturnType();
		if (retVal != null && retVal == target && returnType.isInstance(proxy) ) {
			// Special case: it returned "this" and the return type of the method
			// is type-compatible. Note that we can't help if the target sets
			// a reference to itself in another returned object.
			retVal = proxy;
		}
		else if (retVal == null && returnType != Void.TYPE && returnType.isPrimitive()) {
			throw new AopInvocationException(
					"Null return value from advice does not match primitive return type for: " + method);
		}*/
        return retVal;

    }


}
