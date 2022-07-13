package cn.itcast.reentranLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName ReentranLockTest1
 * @Author ZCC
 * @Date 2022/05/30
 * @Description 锁超时
 * @Version 1.0
 */
@Slf4j(topic = "c.ReentranLockTest3")
public class ReentranLockTest3 {
    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {


            try {
                log.info("尝试获取锁！");
                // if (!lock.tryLock()) {

                try {
                    if (!lock.tryLock(2, TimeUnit.SECONDS)) {

                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //打断锁
                    return;
                }
                log.info("获取到锁！");

            } finally {

                lock.unlock();
            }


        }, "t1");


        lock.lock();
        thread.start();
        Thread.sleep(1000);
        lock.unlock();
    }
}