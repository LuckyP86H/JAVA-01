package com.xianyanyang.jvm.classloader.homework2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 20-实现xlass打包的xar（类似class文件打包的jar）的加载：xar里是xlass。
 * 方案2：通过xlass文件再xar中的路径进行解析
 */
public class XarClassLoaderExt extends ClassLoader {

    private static final String fileSeparator = ".";
    private static final String xlassFileSuffix = "xlass";

    private Map<String, String> xarFilePaths;

    public XarClassLoaderExt(Map<String, String> xarFilePaths) {
        this.xarFilePaths = xarFilePaths;
    }

    @Override
    protected Class<?> findClass(String name) {
        if (!xarFilePaths.containsKey(name)) {
            throw new RuntimeException(String.format("Find xar %s failed", name));
        }
        URL fileUrl = getXarFileUrl(name);

        byte[] result = getFileByte(name, fileUrl);

        return defineClass(name, result, 0, result.length);
    }

    private byte[] getFileByte(String name, URL fileUrl) {
        try (InputStream inputStream = fileUrl.openStream();
             ByteArrayOutputStream ba = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1];
            while (inputStream.read(buffer) != -1) {
                ba.write((byte) (255 - buffer[0]));
            }
            return ba.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Find class %s failed", name));
        }
    }

    private URL getXarFileUrl(String name) {
        String classPath = name.replace(fileSeparator, "/").concat(fileSeparator + xlassFileSuffix);
        String jarFile = xarFilePaths.get(name);
        String xarFilePath = "jar:file:" + jarFile.replace("/", "\\") + "!/" + classPath;
        try {
            return new URL(xarFilePath);
        } catch (MalformedURLException e) {
            throw new RuntimeException("自定义类文件不存在");
        }
    }
}
