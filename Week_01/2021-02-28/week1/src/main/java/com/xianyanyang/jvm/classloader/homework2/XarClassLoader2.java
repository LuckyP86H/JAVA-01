package com.xianyanyang.jvm.classloader.homework2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * hello2.xar中的Hello.xlass的类加载器（Hello.xlass该类通过FileChangeTest工具类生成，解密了之前加密的Class）
 * 方案1：通过JarFile解析xar包
 */
public class XarClassLoader2 extends ClassLoader {

    private static final String fileSeparator = ".";
    private static final String xlassFileSuffix = "xlass";

    private Map<String, String> xarFilePaths;

    public XarClassLoader2(Map<String, String> xarFilePaths) {
        this.xarFilePaths = xarFilePaths;
    }

    @Override
    protected Class<?> findClass(String name) {
        if (!xarFilePaths.containsKey(name)) {
            throw new RuntimeException(String.format("Find xar %s failed", name));
        }

        byte[] result;
        try (JarFile jarFile = new JarFile(new File(xarFilePaths.get(name)));
             InputStream in = jarFile.getInputStream(jarFile.getJarEntry(String.join(fileSeparator, name, xlassFileSuffix)));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1];
            while (in.read(buffer) != -1) {
                out.write((buffer[0]));
            }
            result = out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Find xlass %s failed", name));
        }

        return defineClass(name, result, 0, result.length);
    }
}
