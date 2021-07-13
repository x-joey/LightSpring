package stereotype;

import java.lang.annotation.*;

@Target(ElementType.TYPE)//说明了Annotation所修饰的对象范围
/*ElementType.TYPE用于描述类、接口(包括注解类型) 或enum声明 Class, interface (including annotation type), or enum declaration */
@Retention(RetentionPolicy.RUNTIME)//@Retention可以用来修饰注解，是注解的注解，称为元注解
/**按生命周期来划分可分为3类：
 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 * */
@Documented//@Documented 注解表明这个注解应该被 javadoc工具记录
public @interface Component {

    String value() default "";
}
