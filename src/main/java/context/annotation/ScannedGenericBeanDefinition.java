package context.annotation;

import beans.factory.annotation.AnnotatedBeanDefinition;
import beans.factory.support.GenericBeanDefinition;
import core.type.AnnotationMetadata;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition
        implements AnnotatedBeanDefinition {
    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    @Override
    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
