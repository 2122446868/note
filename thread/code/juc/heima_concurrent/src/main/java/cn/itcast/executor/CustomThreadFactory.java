package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName CustomThreadFactory
 * @Author ZCC
 * @Date 2022/06/01
 * @Description 自定义创建线程的工厂 需要实现ThreadFactory接口
 * @Version 1.0
 */
@Slf4j(topic = "c.CustomThreadFactory")
public class CustomThreadFactory {
    static AtomicInteger threadNum = new AtomicInteger(1);
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            5, 10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("自定义线程创建工厂-" + threadNum.getAndIncrement());

                return thread;
            });

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            String taskName = "任务-" + i;
            executor.execute(() -> {
               log.info(Thread.currentThread().getName() + "处理" + taskName);
            });
        }
        executor.shutdown();

    }
}
