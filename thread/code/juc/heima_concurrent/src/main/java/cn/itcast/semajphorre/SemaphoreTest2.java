package cn.itcast.semajphorre;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName SemaphoreTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description Semaphore信号量 错误释放许可证
 * @Version 1.0
 */
@Slf4j(topic = "c.SemaphoreTest2")
public class SemaphoreTest2 {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                log.info("当前线程；" + Thread.currentThread().getName() + ",获取许可证");
                log.info("当前可用许可证数量：" + semaphore.availablePermits());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                log.info("当前线程；" + Thread.currentThread().getName() + ",释放许可证");
            }
            log.info("当前可用许可证数量：" + semaphore.availablePermits());
        }, "t1");


        Thread t2 = new Thread(() -> {
            try {
                semaphore.acquire();
                log.info("当前线程；" + Thread.currentThread().getName() + ",获取许可证");
                log.info("当前可用许可证数量：" + semaphore.availablePermits());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                log.info("当前线程；" + Thread.currentThread().getName() + ",释放许可证");
            }
            log.info("当前可用许可证数量：" + semaphore.availablePermits());
        }, "t2");

        t1.start();
        Thread.sleep(1000);

        t2.start();
        Thread.sleep(1000);
        t2.interrupt();




    }
}
