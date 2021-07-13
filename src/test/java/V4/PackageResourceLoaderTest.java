package V4;

import core.io.Resource;
import core.io.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
//测试加载某个包下的所有类
public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("dao.v4");
        Assert.assertEquals(2, resources.length);
    }
}
