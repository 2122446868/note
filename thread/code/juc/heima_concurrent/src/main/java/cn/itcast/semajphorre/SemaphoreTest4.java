package cn.itcast.semajphorre;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName SemaphoreTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description Semaphore�ź��� ��ȷ�ͷ����֤
 * @Version 1.0
 */
@Slf4j(topic = "c.SemaphoreTest4")
public class SemaphoreTest4 {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            boolean  acquireSuccess = false;

            try {
                acquireSuccess= semaphore.tryAcquire(1, TimeUnit.SECONDS);
                log.info("��ǰ�̣߳�" + Thread.currentThread().getName() + ",��ȡ���֤");
                log.info("��ǰ�������֤������" + semaphore.availablePermits());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (acquireSuccess){
                    semaphore.release();
                    log.info("��ǰ�̣߳�" + Thread.currentThread().getName() + ",�ͷ����֤");
                }

            }
            log.info("��ǰ�������֤������" + semaphore.availablePermits());
        }, "t1");


        Thread t2 = new Thread(() -> {
            boolean  acquireSuccess = false;

            try {
                acquireSuccess= semaphore.tryAcquire(1, TimeUnit.SECONDS);
                log.info("��ǰ�̣߳�" + Thread.currentThread().getName() + ",��ȡ���֤");
                log.info("��ǰ�������֤������" + semaphore.availablePermits());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (acquireSuccess){
                    semaphore.release();
                    log.info("��ǰ�̣߳�" + Thread.currentThread().getName() + ",�ͷ����֤");
                }
            }
            log.info("��ǰ�������֤������" + semaphore.availablePermits());
        }, "t2");

        t1.start();
        Thread.sleep(1000);

        t1.join();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();




    }
}
