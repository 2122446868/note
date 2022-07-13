package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName ThreadPoolExcetorTest
 * @Author ZCC
 * @Date 2022/06/01
 * @Description 线程池简单使用
 * @Version 1.0
 */
@Slf4j(topic = "c.ThreadPoolExcetorTest")
public class ThreadPoolExcetorTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i <= 10; i++) {
            int j = i;
            String taskName = "任务" + j;
            executor.execute(() -> {

                //模拟业务处理耗时
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info(Thread.currentThread().getName() + taskName + "处理完毕");

            });
        }

        //关闭线程池
        executor.shutdown();
    }
}

