package com.xianyanyang.aop.log.impl;

import com.xianyanyang.aop.log.LogService;

public class LogServiceImpl implements LogService {

    public void log(Object action) {
        System.out.println("log...");
    }
}
