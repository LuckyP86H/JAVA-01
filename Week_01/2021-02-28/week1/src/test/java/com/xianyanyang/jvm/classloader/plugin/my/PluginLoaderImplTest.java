package com.xianyanyang.jvm.classloader.plugin.my;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginLoaderImplTest {

    private PluginLoaderImpl pluginLoader;

    private Map<String, String> plugins;

    private String pluginJarFilePath = "src\\main\\resources\\plugins";

    private Map<String, URLClassLoader> pluginClassLoaders;

    private String myPluginKeyName = "myPlugin";
    private String yourPluginKeyName = "yourPlugin";

    private String myPluginClassName = "com.xianyanyang.plugin.MyPlugin";
    private String yourPluginClassName = "com.xianyanyang.plugin.YourPlugin";


    private String myPluginJarName = "my-plugin.jar";
    private String yourPluginJarName = "your-plugin.jar";

    @Before
    public void setUp() {
        URLClassLoader myPluginClassLoader = preparePluginClassLoader(myPluginJarName);
        URLClassLoader yourPluginClassLoader = preparePluginClassLoader(yourPluginJarName);

        pluginClassLoaders = new HashMap<String, URLClassLoader>() {
            {
                put(myPluginKeyName, myPluginClassLoader);
                put(yourPluginKeyName, yourPluginClassLoader);
            }
        };

        plugins = new HashMap<String, String>() {
            {
                put(myPluginKeyName, myPluginClassName);
                put(yourPluginKeyName, yourPluginClassName);
            }
        };
    }

    @Test
    public void testMyPluginDoPluginProcess() {
        String methodName = "process";
        pluginLoader = new PluginLoaderImpl(pluginClassLoaders, plugins, methodName);
        pluginLoader.doPluginProcess("myPlugin");
        pluginLoader.doPluginProcess("yourPlugin");
    }

    private URLClassLoader preparePluginClassLoader(String jarName) {
        URL[] myUrls = getUrls(new File(pluginJarFilePath), jarName);
        return new URLClassLoader(myUrls);
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
