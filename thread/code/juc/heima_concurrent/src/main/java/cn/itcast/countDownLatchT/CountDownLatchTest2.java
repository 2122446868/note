package cn.itcast.countDownLatchT;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName CountDownLatchTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description 使用CountDownLatch等待所有线程完成
 * @Version 1.0
 */
@Slf4j(topic = "c.CountDownLatchTest2")
public class CountDownLatchTest2 {
    public static class T extends Thread {
        //休眠时间（秒）
        int sleepSeconds;
        CountDownLatch countDownLatch;
        public T(String name, int sleepSeconds, CountDownLatch countDownLatch) {
            super(name);
            this.sleepSeconds = sleepSeconds;
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            Thread ct = Thread.currentThread();
            long startTime = System.currentTimeMillis();
            System.out.println(startTime + "," + ct.getName() + ",开始处理!");
            try {
                //模拟耗时操作，休眠sleepSeconds秒
                TimeUnit.SECONDS.sleep(this.sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + ct.getName() + ",处理完毕,耗时:" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "线程 start!");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        long starTime = System.currentTimeMillis();
        T t1 = new T("解析sheet1线程", 2, countDownLatch);
        t1.start();
        T t2 = new T("解析sheet2线程", 5, countDownLatch);
        t2.start();
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "线程 end!");
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时:" + (endTime - starTime));

    }
}
