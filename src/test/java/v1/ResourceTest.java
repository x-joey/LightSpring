package v1;


import core.io.ClassPathResource;
import core.io.FileSystemResource;
import core.io.Resource;
//import util.Assert;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception {

        Resource r = new ClassPathResource("petstore_v1.xml");

        InputStream is = null;

        try {
            is = r.getInputStream();
            // 注意：这个测试其实并不充分！！
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    @Test
    public void testFileSystemResource() throws Exception {

		Resource r = new FileSystemResource("./src/main/resources/petstore_v1.xml");
		//Resource r = new FileSystemResource("C:\\Users\\liuxin\\git-litespring\\src\\test\\resources\\petstore-v1.xml");

		InputStream is = null;

		try {
			is = r.getInputStream();
			// 注意：这个测试其实并不充分！！
			Assert.assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}

    }

}
