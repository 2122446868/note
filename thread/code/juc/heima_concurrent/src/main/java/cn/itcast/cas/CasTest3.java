package cn.itcast.cas;

import ch.qos.logback.classic.turbo.TurboFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName juc
 * @Package cn.itcast.cas
 * @ClassName CasTest1
 * @Author ZCC
 * @Date 2022/06/02
 * @Description java cas  模拟用户访问网站 统计网张毅用户访问总次数(使用synchronized优化)
 * @Version 1.0
 */
@Slf4j(topic = "c.CasTest1")
public class CasTest3 {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    static ReentrantLock lock = new ReentrantLock();

    //访问次数
    volatile static  int requestCount = 0;
    //模拟访问一次

    public static void request() throws InterruptedException {
        //模拟耗时5毫秒


        TimeUnit.MILLISECONDS.sleep(5);
        //预期的值
        int expectCount;
        do {
            expectCount = getCount();

        } while (!compareAndSwap(expectCount, expectCount + 1));



    }

    /*
    获取当前访问次数
     */
    public static int getCount() {
        return requestCount;
    }

    /**
     * @throws
     * @title compareAndSwap
     * @description
     * @author zcc
     * @param: expectCount  期望count的值
     * @param: newCount     需要给count赋的新值
     * @date 2022/6/2 17:22
     * @return: boolean
     */
    public static synchronized boolean compareAndSwap(int expectCount, int newCount) {
        //判断count当前值是否和期望的expectCount一样，如果一样将newCount赋值给count
        if (getCount() == expectCount) {
            requestCount = newCount;
            return true;

        }
        return false;

    }

    public static void main(String[] args) throws InterruptedException {
        long starTime = System.currentTimeMillis();
        // 100个用户同时访问网站 每个用户访问十次
        int user = 100;
        CountDownLatch countDownLatch = new CountDownLatch(user);
        for (int i = 0; i < user; i++) {
            executorService.execute(() -> {
                //    模拟一个用户访问10次

                boolean falg = false;
                try {
                    for (int j = 0; j < 10; j++) {

                        request();
                        falg = true;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (falg) {

                        countDownLatch.countDown();
                    }
                }


            });


        }

        countDownLatch.await();
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        log.info(Thread.currentThread().getName() + "，耗时：" + (endTime - starTime) + ",count=" + requestCount);
    }
}
