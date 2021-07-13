package aop;

import java.lang.reflect.Method;
//判断方法是否匹配
public interface MethodMatcher {
    boolean matches(Method method/*, Class<?> targetClass*/);
}
