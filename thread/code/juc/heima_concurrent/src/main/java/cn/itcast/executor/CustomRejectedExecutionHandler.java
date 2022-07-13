package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName CustomRejectedExecutionHandler
 * @Author ZCC
 * @Date 2022/06/01
 * @Description 自定义饱和策略 需要实现RejectedExecutionHandler接口
 * @Version 1.0
 */
@Slf4j(topic = "c.CustomRejectedExecutionHandler")
public class CustomRejectedExecutionHandler {
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
            });

    public static void main(String[] args) {
        for (int i = 0; i <= 11; i++) {
            executor.execute(() -> {
                log.info(Thread.currentThread().getName() + "处理");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }

        executor.shutdown();

    }

}
