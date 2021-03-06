import com.xianyanyang.SyncBatchTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SyncBatchTaskTest {

    public static void main(String[] args) throws InterruptedException {
        int taskCount = 10;
        ExecutorService exec = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        for (int i = 0; i < taskCount; i++) {
            exec.execute(new SyncBatchTask());
        }
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000 + "秒");
    }
}