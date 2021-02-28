package com.xianyanyang.jvm.bytecode;

public class MultiByteCodeAnalysis {

    private int count = 0;
    private double sum = 0.0d;

    public void submit(double value) {
        this.count++;
        this.sum++;
    }

    public double getAvg() {
        if (0 == this.count) {
            return sum;
        }
        return this.sum / this.count;
    }

    public void loop(int[] numbers) {
        for (int number : numbers) {
            this.submit(number);
        }
        this.getAvg();
        String value = "a" + "b" + "c";
    }
}
