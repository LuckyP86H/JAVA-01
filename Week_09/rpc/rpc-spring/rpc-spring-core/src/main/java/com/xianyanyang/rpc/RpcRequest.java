package com.xianyanyang.rpc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RpcRequest implements Serializable {

    private String className;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] parameters;
}
