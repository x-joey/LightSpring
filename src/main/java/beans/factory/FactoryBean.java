package beans.factory;

//泛型
public interface FactoryBean<T> {


    T getObject() throws Exception;

    Class<?> getObjectType();

}
