package com.xianyanyang.jvm.classloader.plugin.spi;

import com.xianyanyang.common.plugin.CommonPlugin;
import com.xianyanyang.jvm.classloader.plugin.PluginLoader;
import org.apache.commons.lang.StringUtils;

import java.net.URLClassLoader;
import java.util.Map;
import java.util.ServiceLoader;

public class SPIPluginLoader implements PluginLoader {

    private Map<String, URLClassLoader> pluginClassLoaders;
    private Map<String, String> plugins;

    public SPIPluginLoader(Map<String, URLClassLoader> pluginClassLoaders, Map<String, String> plugins) {
        this.pluginClassLoaders = pluginClassLoaders;
        this.plugins = plugins;
    }

    @Override
    public void doPluginProcess(String pluginName) {
        if (StringUtils.isBlank(pluginName)) {
            throw new RuntimeException("插件名称不能为空");
        }
        if (!plugins.containsKey(pluginName)) {
            throw new RuntimeException(String.format("插件 %s 名称不存在", pluginName));
        }
        if (!pluginClassLoaders.containsKey(pluginName)) {
            throw new RuntimeException(String.format("插件 %s 类加载器不存在", pluginName));
        }

        URLClassLoader pluginClassLoader = pluginClassLoaders.get(pluginName);
        ClassLoader currentLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(pluginClassLoader);
        ServiceLoader<CommonPlugin> services = ServiceLoader.load(CommonPlugin.class);
        try {
            for (CommonPlugin plugin : services) {
                if (plugin.getClass().getName().equals(plugins.get(pluginName))) {
                    plugin.process();
                    break;
                }
            }
        } finally {
            Thread.currentThread().setContextClassLoader(currentLoader);
        }
    }
}
