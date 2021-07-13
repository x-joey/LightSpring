package beans.factory.support;

import beans.BeanDefinition;
import beans.BeansException;
import beans.factory.BeanCreationException;
import beans.factory.FactoryBean;
import beans.factory.config.ConfigurableBeanFactory;
import beans.factory.config.RuntimeBeanReference;
import beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
    //组合ConfigurableBeanFactory
    private final AbstractBeanFactory beanFactory;


    public BeanDefinitionValueResolver(AbstractBeanFactory defaultBeanFactory) {
        this.beanFactory = defaultBeanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {

        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;

        }else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else if (value instanceof BeanDefinition) {
            // Resolve plain BeanDefinition, without contained name: use dummy name.
            BeanDefinition bd = (BeanDefinition) value;

            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));
//打日志所用，暂时不用管
            return resolveInnerBean(innerBeanName, bd);

        }
        else{
            return value;
        }
    }
    //解决嵌套内部bean定义
    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBd) {

        try {

            Object innerBean = this.beanFactory.createBean(innerBd);

            if (innerBean instanceof FactoryBean) {
                try {
                    //生成AspectJBeforeAdivice构造方法中的adviceMethod类型
                    return ((FactoryBean<?>)innerBean).getObject();
                } catch (Exception e) {
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
                }
            }
            else {

                return innerBean;
            }
        }
        catch (BeansException ex) {
            throw new BeanCreationException(
                    innerBeanName,
                    "Cannot create inner bean '" + innerBeanName + "' " +
                            (innerBd != null && innerBd.getBeanClassName() != null ? "of type [" + innerBd.getBeanClassName() + "] " : "")
                    , ex);
        }
    }
}
