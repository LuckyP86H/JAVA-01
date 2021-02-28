package com.xianyanyang.jvm.classloader.salary;

public class ClassLoaderDemo1 {

    public static void main(String[] args) throws ClassNotFoundException {
        //父子关系AppClassLoader->ExtClassLoader->BootStrapClassLoader
        ClassLoader cl1 = ClassLoaderDemo1.class.getClassLoader();
        //cl1 > sun.misc.Launcher$AppClassLoader
        System.out.println("cl1 >>>> " + cl1);
        //parent of c1> sun.misc.Launcher$ExtClassLoader
        System.out.println("parent of c1 >>>> " + cl1.getParent());
        //BootStrapClassLoader由C++开发，是JVM虚拟机的一部分，本身不是JAVA类
        //grant parent of c1>null
        System.out.println("grant parent of c1 >>>> " + cl1.getParent().getParent());
        //String，Integer等基础类由BootStrapClassLoader加载。
        ClassLoader cl2 = String.class.getClassLoader();
        System.out.println("cl2 >>>> " + cl2);
        System.out.println(cl1.loadClass("java.util.List").getClass().getClassLoader());

        //Java 指令可以通过-verbose:class -verbose:gc 参数在启动时打印出类加载情况
        //BootStrapClassLoader 记载java基础类，这个属性不能在Java指令中定义，推断不是由java语言处理
        System.out.println("BootStrapClassLoader加载目录为："+System.getProperty("sun.boot.class.path"));

        //ExtClassLoader加载JAVA_HOME/ext下的jat包，可通过-D java.ext.dirs另行指定目录
        System.out.println("ExtClassLoader加载目录："+System.getProperty("java.ext.dirs"));
        //AppClassLoader加载CLASSPATH应用下的jar包。可通过-D java.class.path另行指定目录
        System.out.println("AppClassLoader加载目录："+System.getProperty("java.class.path"));
    }
}
