package core.type;

import core.annotation.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata {

    Set<String> getAnnotationTypes();
    boolean hasAnnotation(String annotationType);
    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
