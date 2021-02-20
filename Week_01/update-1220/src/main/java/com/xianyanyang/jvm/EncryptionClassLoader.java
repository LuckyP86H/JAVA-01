package com.xianyanyang.jvm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 加密类类加载器
 */
public class EncryptionClassLoader extends ClassLoader {

    private Map<String, String> encryptionClassFilePaths;

    public EncryptionClassLoader(Map<String, String> encryptionClassFilePaths) {
        this.encryptionClassFilePaths = encryptionClassFilePaths;
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            byte[] result = getBytes(name);
            return defineClass(name, result, 0, result.length);
        } catch (IOException e) {
            throw new RuntimeException(String.format("find class  %s failed", name));
        }
    }

    //region private Method

    private byte[] getBytes(String name) throws IOException {
        InputStream in = EncryptionClassLoader.class.getResourceAsStream(encryptionClassFilePaths.get(name));
        byte[] bytes = toByteArray(in);
        in.close();
        return decode(bytes);
    }

    private byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    private byte[] decode(byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) (255 - bytes[i]);
        }
        return result;
    }

    //endregion
}
