package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName TestWaitNotify
 * @Author ZCC
 * @Date 2022/04/08
 * @Description TODO
 * @Version 1.0
 */
@Slf4j(topic = "c.TestWaitNotify")
public class TestWaitNotify {
    final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (obj) {
                log.debug("执行....");
                try {
                    log.debug(Thread.currentThread().getName() + "：" + Thread.currentThread().getState());
                    obj.wait();
                    log.debug(Thread.currentThread().getName() + "：" + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码...."); // 断点
            }
            log.debug(Thread.currentThread().getName() + "：" + Thread.currentThread().getState());
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (obj) {
                log.debug("执行....");
                try {
                    log.debug(Thread.currentThread().getName() + "：" + Thread.currentThread().getState());
                    obj.wait();
                    log.debug(Thread.currentThread().getName() + "：" + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码...."); // 断点
            }
            log.debug(Thread.currentThread().getName() + "：" + Thread.currentThread().getState());
        }, "t2");

        log.debug(t1.getName() + "：" + t1.getState());
        log.debug(t2.getName() + "：" + t2.getState());
        t1.start();
        log.debug(t1.getName() + "：" + t1.getState());
        t2.start();
        log.debug(t2.getName() + "：" + t2.getState());


        TimeUnit.SECONDS.sleep(1);
        log.debug("唤醒 obj 上其它线程");
        synchronized (obj) {
            obj.notifyAll(); // 唤醒obj上所有等待线程 断点
        }
    }
}


