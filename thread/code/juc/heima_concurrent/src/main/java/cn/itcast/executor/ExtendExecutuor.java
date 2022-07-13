package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName ExtendEXecuteros
 * @Author ZCC
 * @Date 2022/06/01
 * @Description 线程池扩展
 * @Version 1.0
 */
@Slf4j(topic = "c.ExtendExecutuor")
public class ExtendExecutuor {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            5,
            5,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(1),
            Executors.defaultThreadFactory(),
            (r, executors) -> {
                //自定义饱和策略
                //记录一下无法处理的任务
                log.info("无法处理的任务：" + r.toString());
            }) {
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println(System.currentTimeMillis() + "," + t.getName() + ",开始执行任务:" + r.toString());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",任务:" + r.toString() + "，执行完毕!");
        }

        @Override
        protected void terminated() {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，关闭线程池!");
        }
    };

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            String taskName = "任务-" + i;
            executor.execute(() -> {
                log.info(Thread.currentThread().getName() + "处理" + taskName);
            });
        }
        TimeUnit.SECONDS.sleep(1);
        executor.shutdown();

        System.out.println(Runtime.getRuntime().availableProcessors());

    }
}
