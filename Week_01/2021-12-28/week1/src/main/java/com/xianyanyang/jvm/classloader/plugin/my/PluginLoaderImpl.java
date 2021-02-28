package com.xianyanyang.jvm.classloader.plugin.my;

import com.xianyanyang.jvm.classloader.plugin.PluginLoader;
import com.xianyanyang.jvm.classloader.plugin.ThreadContextClassLoaderSwapper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * 30-基于自定义Classloader实现类的动态加载和卸载：需要设计加载和卸载。
 * 30-基于自定义Classloader实现模块化机制：需要设计模块化机制。
 * 30-使用xar作为模块，实现xar动态加载和卸载：综合应用前面的内容。
 * 通过自定义策略模式获取指定的类加载器，通过类加载器反射加载出执行的类
 */
public class PluginLoaderImpl implements PluginLoader {

    private Map<String, URLClassLoader> pluginClassLoaders;
    private String methodName;
    private Map<String, String> plugins;

    public PluginLoaderImpl(Map<String, URLClassLoader> pluginClassLoaders, Map<String, String> plugins, String methodName) {
        this.pluginClassLoaders = pluginClassLoaders;
        this.plugins = plugins;
        this.methodName = methodName;
    }

    /**
     * 执行指定名称的插件
     *
     * @param pluginName 插件名称
     */
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
        String pluginClassFullName = plugins.get(pluginName);
        URLClassLoader classLoader = pluginClassLoaders.get(pluginName);
        try {
            ThreadContextClassLoaderSwapper.replace(classLoader);
            Class<?> pluginClass = classLoader.loadClass(pluginClassFullName);
            Method method = pluginClass.getMethod(methodName);
            method.invoke(pluginClass.getDeclaredConstructor().newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("执行插件异常，请核实。", e);
        } finally {
            ThreadContextClassLoaderSwapper.restore();
        }
    }
}
