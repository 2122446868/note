package cn.itcast.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.cas
 * @ClassName CasTest1
 * @Author ZCC
 * @Date 2022/06/02
 * @Description java cas  模拟用户访问网站 统计网张毅用户访问总次数(使用synchronized)
 * @Version 1.0
 */
@Slf4j(topic = "c.CasTest1")
public class CasTest2 {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    //访问次数
    static   int requestCount = 0;
    //模拟访问一次

    public static synchronized void  request() throws InterruptedException {
        //模拟耗时5毫秒


        TimeUnit.MILLISECONDS.sleep(5);
        requestCount++;


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
