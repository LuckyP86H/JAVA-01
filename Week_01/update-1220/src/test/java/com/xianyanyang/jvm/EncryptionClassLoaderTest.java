package com.xianyanyang.jvm;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 加密类类加载器测试用例
 */
public class EncryptionClassLoaderTest {

    private EncryptionClassLoader encryptionClassLoader;

    private String encryptionClassFilePath = "/jvm/Hello.xlass";
    private String className = "Hello";
    private String methodName = "hello";

    private Map<String, String> encryptionClassFilePaths = new HashMap<String, String>() {
        {
            put(className, encryptionClassFilePath);
        }
    };

    @Before
    public void setUp() {
        encryptionClassLoader = new EncryptionClassLoader(encryptionClassFilePaths);
    }

    @Test
    public void loadEncryptionClass_invokeMethod() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = encryptionClassLoader.findClass(className);
        Method method = clazz.getMethod(methodName);
        method.invoke(clazz.getDeclaredConstructor().newInstance());
    }
}
