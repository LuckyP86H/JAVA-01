package com.xianyanyang.jvm.classloader.salary;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.SecureClassLoader;

/**
 * 从.myClass文件中加载对象
 */
public class SalaryXlassLoader extends SecureClassLoader {
    private String classPath;

    public SalaryXlassLoader(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 需要实现从.myclass文件中加载
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = this.classPath + "\\SalaryCaler.xlass";
        byte[] b = null;
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        int code;
        try (FileInputStream fis = new FileInputStream(new File(filePath));) {
            int header = fis.read();
            while ((code = fis.read()) != -1) {
                ba.write(code);
            }
            b = ba.toByteArray();
            return this.defineClass(name, b, 0, b.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("自定义类文件不存在");
        }
    }
}
