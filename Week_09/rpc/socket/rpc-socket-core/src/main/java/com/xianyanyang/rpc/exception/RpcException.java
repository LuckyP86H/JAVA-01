package com.xianyanyang.rpc.exception;

public class RpcException extends RuntimeException {
    private final String code;

    public String getCode() {
        return code;
    }

    public RpcException(String code, String message) {
        super(message);
        this.code = code;
    }
}
