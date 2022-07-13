package cn.itcast.semajphorre;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName SemaphoreTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description Semaphore�ź��� ��ȷ�ͷ����֤
 * @Version 1.0
 */
@Slf4j(topic = "c.SemaphoreTest3")
public class SemaphoreTest3 {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            boolean  acquireSuccess = false;

            try {
                semaphore.acquire();
                acquireSuccess = true;
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
                semaphore.acquire();
                acquireSuccess = true;
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

        t2.start();
        Thread.sleep(1000);
        t2.interrupt();




    }
}
