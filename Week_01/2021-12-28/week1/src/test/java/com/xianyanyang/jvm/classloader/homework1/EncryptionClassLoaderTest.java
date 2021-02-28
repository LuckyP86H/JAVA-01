package com.xianyanyang.jvm.classloader.homework1;

import com.xianyanyang.jvm.classloader.homework1.EncryptionClassLoader;
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

    private String encryptionClassFilePath;
    private String className;
    private String methodName;

    private Map<String, String> encryptionClassFilePaths;

    @Before
    public void setUp() {
        encryptionClassFilePath = "/jvm/Hello.xlass";
        className = "Hello";
        methodName = "hello";
        encryptionClassFilePaths = new HashMap<String, String>() {
            {
                put(className, encryptionClassFilePath);
            }
        };

        encryptionClassLoader = new EncryptionClassLoader(encryptionClassFilePaths);
    }

    @Test
    public void loadEncryptionClass_invokeMethod() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = encryptionClassLoader.loadClass(className);
        Method method = clazz.getMethod(methodName);
        method.invoke(clazz.getDeclaredConstructor().newInstance());
    }
}