package com.xianyanyang.jvm.classloader.salary;

import org.junit.Test;

public class SalaryJarLoaderTest {

    @Test
    public void test() throws Exception {
        Double salary = 2000d;
        Double money;

        String jarFile = "D:\\lib\\salary.xar";
        ClassLoader classLoader = new SalaryJarLoader(jarFile);
        while (true) {
            money = calSalary(salary, classLoader);
            System.out.println("实际到手的金额:" + money);
            Thread.sleep(1000);
        }
    }

    private static Double calSalary(Double salary, ClassLoader classLoader) throws Exception {
        Class<?> clazz = classLoader.loadClass("com.xianyanyang.SalaryCaler");
        Object obj = clazz.getDeclaredConstructor().newInstance();
        return (Double) clazz.getMethod("cal", Double.class).invoke(obj, salary);
    }
}
