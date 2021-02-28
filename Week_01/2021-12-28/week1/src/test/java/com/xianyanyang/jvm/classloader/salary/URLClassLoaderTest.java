package com.xianyanyang.jvm.classloader.salary;

import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderTest {

    public static void main(String[] args) throws Exception {
        Double salary = 2000d;
        Double money;

        URL jarPath = new URL("file:D:\\lib\\salary-1.0.0.jar");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarPath});
        while (true) {
            money = calSalary(salary, urlClassLoader);
            System.out.println("实际到手的金额:" + money);
            Thread.sleep(1000);
        }
    }

    private static Double calSalary(Double salary, URLClassLoader urlClassLoader) throws Exception {
        Class<?> clazz = urlClassLoader.loadClass("com.xianyanyang.SalaryCaler");
        Object obj = clazz.getDeclaredConstructor().newInstance();
        return (Double) clazz.getMethod("cal", Double.class).invoke(obj, salary);
    }
}
