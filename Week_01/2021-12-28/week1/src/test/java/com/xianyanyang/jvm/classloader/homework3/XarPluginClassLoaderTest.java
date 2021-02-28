package com.xianyanyang.jvm.classloader.homework3;

import com.xianyanyang.jvm.classloader.homework2.XarClassLoader;
import com.xianyanyang.jvm.classloader.homework2.XarClassLoader2;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XarPluginClassLoaderTest {

    private XarPluginClassLoader pluginLoader;

    private Map<String, ClassLoader> pluginClassLoaders;

    private String plugin = "hello";
    private String plugin2 = "hello2";

    private String className = "Hello";


    private String xarName = "hello.xar";
    private String xarName2 = "hello2.xar";

    private String xarFilePath = "src\\main\\resources\\jvm";
    private String xarFilePath2 = "src\\main\\resources\\jvm\\hello2";

    @Before
    public void setUp() {
        URL[] urls1 = this.getUrls(new File(xarFilePath), xarName);
        XarClassLoader xarClassLoader = new XarClassLoader(new HashMap<String, String>() {
            {

                put(className, urls1[0].getFile());
            }
        });
        URL[] urls2 = this.getUrls(new File(xarFilePath2), xarName2);

        XarClassLoader2 xarClassLoader2 = new XarClassLoader2(new HashMap<String, String>() {
            {

                put(className, urls2[0].getFile());
            }
        });
        pluginClassLoaders = new HashMap<String, ClassLoader>() {
            {
                put(plugin, xarClassLoader);
                put(plugin2, xarClassLoader2);
            }
        };
    }

    @Test
    public void testXarPluginClassLoader() {
        String methodName = "hello";
        pluginLoader = new XarPluginClassLoader(pluginClassLoaders, methodName);
        pluginLoader.doPluginProcess(plugin);
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
