package V4;

import beans.factory.annotation.AutowiredAnnotationProcessor;
import beans.factory.annotation.AutowiredFieldElement;
import beans.factory.annotation.InjectionElement;
import beans.factory.annotation.InjectionMetadata;
import beans.factory.config.DependencyDescriptor;
import beans.factory.support.DefaultBeanFactory;
import dao.v4.AccountDao;
import dao.v4.ItemDao;
import org.junit.Assert;
import org.junit.Test;
import service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.List;

public class AutowiredAnnotationProcessorTest {
    //mock一个xml读取
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();
    //匿名类
    DefaultBeanFactory beanFactory = new DefaultBeanFactory(){
        public Object resolveDependency(DependencyDescriptor descriptor){
            if(descriptor.getDependencyType().equals(AccountDao.class)){
                return accountDao;
            }
            if(descriptor.getDependencyType().equals(ItemDao.class)){
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };


    @Test
    public void testGetInjectionMetadata(){
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();//拿到字段元素
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements,"accountDao");
        assertFieldExists(elements,"itemDao");
    }

    private void assertFieldExists(List<InjectionElement> elements ,String fieldName){
        for(InjectionElement ele : elements){
            AutowiredFieldElement fieldEle = (AutowiredFieldElement)ele;
            Field f = fieldEle.getField();
            if(f.getName().equals(fieldName)){
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }
}
