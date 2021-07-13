package beans.factory.config;
//运行时bean相关，获取bean名称
public class RuntimeBeanReference {
    private final String beanName;
    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }
    public String getBeanName() {
        return this.beanName;
    }

}
