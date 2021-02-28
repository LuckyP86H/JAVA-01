package com.xianyanyang.jvm.classloader.salary;

public class SalaryXlassLoaderTest {

    public static void main(String[] args) throws Exception {
        Double salary = 2000d;
        Double money;

        String classPath = "D:\\lib";
        ClassLoader urlClassLoader = new SalaryXlassLoader(classPath);
        while (true) {
            money = calSalary(salary, urlClassLoader);
            System.out.println("实际到手的金额:" + money);
            Thread.sleep(1000);
        }
    }

    private static Double calSalary(Double salary, ClassLoader urlClassLoader) throws Exception {
        Class<?> clazz = urlClassLoader.loadClass("com.xianyanyang.SalaryCaler");
        System.out.println(clazz.getClassLoader());
        System.out.println(clazz.getClassLoader().getParent());
        Object obj = clazz.getDeclaredConstructor().newInstance();
        return (Double) clazz.getMethod("cal", Double.class).invoke(obj, salary);
    }
}
