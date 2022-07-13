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
 * @Description ʹ��CountDownLatch�ȴ������߳����
 * @Version 1.0
 */
@Slf4j(topic = "c.CountDownLatchTest2")
public class CountDownLatchTest2 {
    public static class T extends Thread {
        //����ʱ�䣨�룩
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
            System.out.println(startTime + "," + ct.getName() + ",��ʼ����!");
            try {
                //ģ���ʱ����������sleepSeconds��
                TimeUnit.SECONDS.sleep(this.sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + ct.getName() + ",�������,��ʱ:" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "�߳� start!");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        long starTime = System.currentTimeMillis();
        T t1 = new T("����sheet1�߳�", 2, countDownLatch);
        t1.start();
        T t2 = new T("����sheet2�߳�", 5, countDownLatch);
        t2.start();
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "�߳� end!");
        long endTime = System.currentTimeMillis();
        System.out.println("�ܺ�ʱ:" + (endTime - starTime));

    }
}
