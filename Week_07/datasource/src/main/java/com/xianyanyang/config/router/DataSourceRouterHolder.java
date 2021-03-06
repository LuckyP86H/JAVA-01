package com.xianyanyang.config.router;

import com.xianyanyang.config.annotation.DataBaseType;
import org.springframework.util.Assert;

public class DataSourceRouterHolder {

    private static ThreadLocal<DataBaseType> CONTEXT = new ThreadLocal<>();

    public static void set(DataBaseType dataBaseType) {
        Assert.notNull(dataBaseType, "数据库类型为空");
        CONTEXT.set(dataBaseType);
    }

    public static DataBaseType getClientDatabase() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
