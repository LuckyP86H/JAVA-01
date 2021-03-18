package com.xianyanyang.rpc.register;

import com.xianyanyang.rpc.URL;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

/**
 * 服务注册中心
 */
public class RegisterCenter {

    private static Map<String, List<URL>> services = new HashMap<>();

    public static List<URL> getUrls(String className) {
        services = getFile();
        return services.get(className);
    }

    public static void registerService(String name, URL url) {
        List<URL> urls = services.get(name);
        if (CollectionUtils.isEmpty(urls)) {
            services.put(name, Arrays.asList(url));
            saveFile();
            return;
        }
        urls.add(url);
        saveFile();
    }

    private static void saveFile() {
        try {
            File file = FileUtils.getFile("d:/temp.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream("d:/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(services);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, List<URL>> getFile() {

        try {
            File file = FileUtils.getFile("d:/temp.txt");
            if (!file.exists()) {
                file.createNewFile();
                return Collections.emptyMap();
            }
            FileInputStream fileInputStream = new FileInputStream("d:/temp.txt");
            if (file.length() == 0) {
                fileInputStream.close();
                return Collections.emptyMap();
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
