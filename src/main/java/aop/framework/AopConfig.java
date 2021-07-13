package aop.framework;


import aop.Advice;

import java.lang.reflect.Method;
import java.util.List;
//配置信息，为了让factory去创建代理
public interface AopConfig  {


    Class<?> getTargetClass();

    Object getTargetObject();

    boolean isProxyTargetClass();


    Class<?>[] getProxiedInterfaces();


    boolean isInterfaceProxied(Class<?> intf);


    List<Advice> getAdvices();


    void addAdvice(Advice advice) ;

    List<Advice> getAdvices(Method method/*,Class<?> targetClass*/);

    void setTargetObject(Object obj);


}
