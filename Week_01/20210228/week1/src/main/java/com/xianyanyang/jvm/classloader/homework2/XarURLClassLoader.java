package com.xianyanyang.jvm.classloader.homework2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarFile;

public class XarURLClassLoader extends ClassLoader {

    private static final String fileSeparator = ".";
    private static final String xlassFileSuffix = "xlass";

    private Map<String, String> xarFilePaths;

    public XarURLClassLoader(Map<String, String> xarFilePaths) {
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
                out.write((byte) (255 - buffer[0]));
            }
            result = out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Find class %s failed", name));
        }

        return defineClass(name, result, 0, result.length);
    }
}
