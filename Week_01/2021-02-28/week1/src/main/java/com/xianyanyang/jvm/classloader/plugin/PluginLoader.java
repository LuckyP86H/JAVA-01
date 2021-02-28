package com.xianyanyang.jvm.classloader.plugin;

public interface PluginLoader {

    /**
     * 执行指定名称的插件
     *
     * @param pluginName 插件名称
     */
    void doPluginProcess(String pluginName);
}
