package com.xianyanyang.jvm.classloader.homework2;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XarURLClassLoaderTest {

    private XarURLClassLoader xarURLClassLoader;

    private Map<String, String> xarFilePaths;

    private String className = "Hello";

    private String xarFilePath = "src\\main\\resources\\jvm";

    @Before
    public void setUp() {
        /*jar cvfM hello.xar Hello.xlass*/
        URL[] urls = this.getUrls(new File(xarFilePath), "hello.xar");
        xarFilePaths = new HashMap<String, String>() {
            {

                put(className, urls[0].getFile());
            }
        };
        xarURLClassLoader = new XarURLClassLoader(xarFilePaths);
    }

    @Test
    public void loadXar_xlass_invokeMethod() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = xarURLClassLoader.loadClass(className);
        Method method = clazz.getMethod("hello");
        method.invoke(clazz.getDeclaredConstructor().newInstance());
    }

    private URL[] getUrls(File dir, String fileName) {
        List<URL> results = new ArrayList<>();
        try {
            Files.newDirectoryStream(dir.toPath(), fileName).forEach(path -> results.add(getUrl(path)));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return results.toArray(new URL[0]);
    }

    private URL getUrl(Path path) {
        try {
            return path.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
