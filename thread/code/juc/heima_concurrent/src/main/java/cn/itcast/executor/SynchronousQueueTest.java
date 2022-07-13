package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName SynchronousQueueTest
 * @Author ZCC
 * @Date 2022/06/01
 * @Description SynchronousQueue 同步队列测试
 * @Version 1.0
 */
@Slf4j(topic = "c.SynchronousQueueTest")
public class SynchronousQueueTest {
    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            int j = i;
            String taskName = "任务" + j;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" + taskName);
                //模拟任务内部处理耗时
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
