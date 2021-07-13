package V4;


import core.annotation.AnnotationAttributes;
import core.io.ClassPathResource;
import core.type.AnnotationMetadata;
import core.type.classreading.MetadataReader;
import core.type.classreading.SimpleMetadataReader;
import org.junit.Assert;
import org.junit.Test;
import stereotype.Component;

import java.io.IOException;

//对classReader进一步封装
public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("./service/v4/PetStoreService.class");
        /**
         * 之前是ClassReader reader = new ClassReader(resource.getInputStream());
         * 现在MetadataReader封装ClassReader
         * SimpleMetadataReader封装资源获取
         */
        MetadataReader reader = new SimpleMetadataReader(resource);
        //注意：不需要单独使用ClassMetadata
        //ClassMetadata clzMetadata = reader.getClassMetadata();
        AnnotationMetadata amd = reader.getAnnotationMetadata();
        String annotation = Component.class.getName();
        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));

        //注：下面对class metadata的测试并不充分
        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("service.v4.PetStoreService", amd.getClassName());
    }
}
