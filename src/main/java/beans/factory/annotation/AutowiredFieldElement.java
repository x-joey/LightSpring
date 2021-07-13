package beans.factory.annotation;

import beans.factory.BeanCreationException;
import beans.factory.config.AutowireCapableBeanFactory;
import beans.factory.config.DependencyDescriptor;
import util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

//注入字段
public class AutowiredFieldElement extends InjectionElement{
    boolean required;
    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f,factory);
        this.required = required;
    }
    public Field getField(){
        return (Field)this.member;
    }
    @Override
    public void inject(Object target) {
        Field field = this.getField();
        try {

            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);

            Object value = factory.resolveDependency(desc);

            if (value != null) {

                ReflectionUtils.makeAccessible(field);
                field.set(target, value);//关键是通过反射set方法注入
            }
        }
        catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }
}
