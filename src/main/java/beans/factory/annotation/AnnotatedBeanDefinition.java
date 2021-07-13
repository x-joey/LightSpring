package beans.factory.annotation;

import beans.BeanDefinition;
import core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}
