package cn.itcast.reentranLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName ReentranLockTest1
 * @Author ZCC
 * @Date 2022/05/30
 * @Description 可打断锁
 * @Version 1.0
 */
@Slf4j(topic = "c.ReentranLockTest2")
public class ReentranLockTest2 {
    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            //可打断锁
            //如果没有竞争那么次方法获取lock对象锁
            //如果有竞争就进入阻塞队列，可以被其它方法用interrupt方法打断
            log.debug("获取锁");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("打断锁");
                //打断之后不往下执行
                return;
            }

            try {
                log.info("获取到锁");

            } finally {
                lock.unlock();
            }

        }, "t1");

        lock.lock();
        thread.start();

        Thread.sleep(1000);
        //打断
        thread.interrupt();


    }
}