package com.xianyanyang.starter;

public class School implements ISchool {

    private String name;

    public String getName() {
        return name;
    }

    public School setName(String name) {
        this.name = name;
        return this;
    }

    private Klass class1;

    public Klass getClass1() {
        return class1;
    }

    public School setClass1(Klass class1) {
        this.class1 = class1;
        return this;
    }

    @Override
    public void ding() {
        System.out.println("School" + this.name + " have class" + this.class1.getName());
    }

}
