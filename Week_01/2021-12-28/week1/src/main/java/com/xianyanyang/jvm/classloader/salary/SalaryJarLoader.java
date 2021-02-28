package com.xianyanyang.jvm.classloader.salary;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureClassLoader;

/**
 * 实现从jar包中找到class文件来加载类
 */
public class SalaryJarLoader extends SecureClassLoader {

    private String jarFile;

    public SalaryJarLoader(String jarFile) {
        this.jarFile = jarFile;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("重新加载类");
        String classPath = name.replace(".", "/").concat(".class");
        URL fileUrl;
        String filePath = "jar:file:\\" + jarFile + "!/" + classPath;
        try {
            fileUrl = new URL(filePath);
        } catch (MalformedURLException e) {
            throw new ClassNotFoundException("自定义类文件不存在");
        }

        byte[] b;
        int code;
        try (InputStream inputStream = fileUrl.openStream();
             ByteArrayOutputStream ba = new ByteArrayOutputStream()) {
            while ((code = inputStream.read()) != -1) {
                ba.write(code);
            }
            b = ba.toByteArray();
            return this.defineClass(name, b, 0, b.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("自定义类文件不存在");
        }
    }
}
