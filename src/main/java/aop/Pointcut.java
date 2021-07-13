package aop;

public interface Pointcut {
    MethodMatcher getMethodMatcher();//获取方法对象
    String getExpression();//获取表达式
}
