package com.xianyanyang.jvm.classloader.homework1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 加密类类加载器
 * 10-使用自定义Classloader机制，实现xlass的加载：xlass是作业材料。
 */
public class EncryptionClassLoader extends ClassLoader {

    private Map<String, String> encryptionClassFilePaths;

    public EncryptionClassLoader(Map<String, String> encryptionClassFilePaths) {
        this.encryptionClassFilePaths = encryptionClassFilePaths;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] result = getBytes(name);
        System.out.println(result);
        return defineClass(name, result, 0, result.length);
    }

    //region private Method

    private byte[] getBytes(String name) {
        try (InputStream in = EncryptionClassLoader.class.getResourceAsStream(encryptionClassFilePaths.get(name));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1];
            while (in.read(buffer) != -1) {
                out.write((byte) (255 - buffer[0]));
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Find class %s failed", name));
        }
    }

    //endregion
}
