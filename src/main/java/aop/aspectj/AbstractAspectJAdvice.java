package aop.aspectj;

import aop.Advice;
import aop.Pointcut;
import aop.config.AspectInstanceFactory;


import java.lang.reflect.Method;

//实现增强接口
public abstract class AbstractAspectJAdvice implements Advice {
    protected Method adviceMethod;//增强方法
    protected AspectJExpressionPointcut pointcut;//需要增强的位置、方法
    //protected Object adviceObject;//增强方法所在类
    protected AspectInstanceFactory adviceObjectFactory;//advice对象工厂

    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObjectFactory = adviceObjectFactory;
    }
    //重点方法，增强方法唤醒增强方法所在类
    public void invokeAdviceMethod() throws  Throwable{

        adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
    }
    public Pointcut getPointcut(){
        return this.pointcut;
    }
    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceInstance() throws Exception {
        return adviceObjectFactory.getAspectInstance();
    }
}
