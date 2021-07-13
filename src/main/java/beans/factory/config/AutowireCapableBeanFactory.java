package beans.factory.config;

import beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    public Object resolveDependency(DependencyDescriptor descriptor);
}
