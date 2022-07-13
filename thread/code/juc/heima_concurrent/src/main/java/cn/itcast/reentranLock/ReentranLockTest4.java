package cn.itcast.reentranLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName ReentranLockTest1
 * @Author ZCC
 * @Date 2022/05/30
 * @Description RenntrantLock 条件变量
 * @Version 1.0
 */
@Slf4j(topic = "c.ReentranLockTestReentranLockTest4")
public class ReentranLockTest4 {
    public static ReentrantLock lock = new ReentrantLock();
    static  Condition condition1 = lock.newCondition();
    static  Condition condition2 = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(() -> {


            try {
                log.info("获取锁");
                lock.lock();

                log.info("等待");
                //等待
                condition1.await();

            } catch (Exception e) {
                log.info("error");
                e.printStackTrace();
            } finally {

                log.info("释放锁");
                lock.unlock();
            }
        }, "t1");


        t1.start();

        Thread.sleep(2000);
        lock.lock();
        //唤醒
        condition1.signal();
        lock.unlock();

    }
}