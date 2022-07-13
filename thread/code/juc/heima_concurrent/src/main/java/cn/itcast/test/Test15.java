package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName Test15
 * @Author ZCC
 * @Date 2022/04/08
 * @Description TODO
 * @Version 1.0
 */
@Slf4j(topic = "c.Test15")
public class Test15 {
    static int counter = 0;
    static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock){
                    counter++;
                }

            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock){
                    counter--;
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", counter);
    }

}
