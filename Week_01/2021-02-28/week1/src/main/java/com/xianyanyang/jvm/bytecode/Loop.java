package com.xianyanyang.jvm.bytecode;

public class Loop {
    private int result = 0;

    public void loop(int[] ages) {
        for (int age : ages) {
            result += age;
        }
    }
}