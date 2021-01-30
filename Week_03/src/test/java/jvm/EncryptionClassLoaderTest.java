package jvm;

import org.junit.Before;
import org.junit.Test;

/**
 * 加密类类加载器测试用例
 */
public class EncryptionClassLoaderTest {

    private EncryptionClassLoader encryptionClassLoader;

    private String encryptionClassFilePath = "/jvm/Hello.xlass";
    private String className = "Hello";
    private String methodName = "hello";

    @Before
    public void setUp() {
        encryptionClassLoader = new EncryptionClassLoader(encryptionClassFilePath, className, methodName);
    }

    @Test
    public void loadEncryptionClass_invokeMethod() {
        encryptionClassLoader.invokeMethod();
    }
}
