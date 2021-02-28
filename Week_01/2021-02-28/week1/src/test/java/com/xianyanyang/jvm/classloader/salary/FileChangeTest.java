package com.xianyanyang.jvm.classloader.salary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileChangeTest {

    public static void main(String[] args) throws Exception {
        File file1 = new File("src\\main\\resources\\jvm\\Hello.xlass");
        File file2 = new File("src\\main\\resources\\jvm\\hello2\\Hello.xlass");
        String value = "";
        int code = 1;
        try (FileInputStream fileInputStream = new FileInputStream(file1);
             FileOutputStream fileOutputStream = new FileOutputStream(file2)) {
            while ((code = fileInputStream.read()) != -1) {
                code = 255 - code;
                value = value + code;
                fileOutputStream.write(code);
            }
        }
        System.out.println(value);
    }
}
