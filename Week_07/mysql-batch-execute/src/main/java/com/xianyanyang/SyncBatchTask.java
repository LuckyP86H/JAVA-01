package com.xianyanyang;

import com.xianyanyang.batch.JdbcOperation;

/**
 * 异步批量操作任务
 */
public class SyncBatchTask implements Runnable {

    @Override
    public void run() {
        JdbcOperation.batchInsertRows(100000);
    }
}