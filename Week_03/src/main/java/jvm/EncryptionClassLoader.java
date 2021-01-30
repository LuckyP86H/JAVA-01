package jvm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * 加密类类加载器
 */
public class EncryptionClassLoader extends ClassLoader {

    private String encryptionClassFilePath;
    private String className;
    private String methodName;

    public EncryptionClassLoader(String encryptionClassFilePath, String className, String methodName) {
        this.encryptionClassFilePath = encryptionClassFilePath;
        this.className = className;
        this.methodName = methodName;
    }

    /**
     * 执行指定类的方法
     */
    public void invokeMethod() {
        try {
            Class clazz = new EncryptionClassLoader(encryptionClassFilePath, className, methodName).findClass(className);
            clazz.getMethod(methodName).invoke(clazz.getDeclaredConstructor().newInstance());
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("invoke method failed");
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            byte[] result = getBytes();
            return defineClass(name, result, 0, result.length);
        } catch (IOException e) {
            throw new RuntimeException("find class failed");
        }
    }


    //region private Method

    private byte[] getBytes() throws IOException {
        InputStream in = EncryptionClassLoader.class.getResourceAsStream(encryptionClassFilePath);
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
