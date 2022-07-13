package cn.itcast.countDownLatchT;

import lombok.extern.slf4j.Slf4j;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName CountDownLatchTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description 使用join 等待所有线程完成
 * @Version 1.0
 */
@Slf4j(topic = "c.CountDownLatchTest1")
public class CountDownLatchTest1 {
    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();

        Thread t1 = new Thread(() -> {
            long start = System.currentTimeMillis();
            log.info("当前线程：" + Thread.currentThread().getName()+"开始");
            try {
                //模拟业务执行时间
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("当前线程：" + Thread.currentThread().getName()+"结束 执行时间：" + (System.currentTimeMillis() - start));


        }, "t1");


        Thread t2 = new Thread(() -> {
            long start = System.currentTimeMillis();
            log.info("当前线程：" + Thread.currentThread().getName()+"开始");
            try {
                //模拟业务执行时间
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("当前线程：" + Thread.currentThread().getName()+"结束 执行时间：" + (System.currentTimeMillis() - start));

        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        log.info("当前线程：" + Thread.currentThread().getName()+"结束 执行时间：" + (System.currentTimeMillis() - l));
    }
}
