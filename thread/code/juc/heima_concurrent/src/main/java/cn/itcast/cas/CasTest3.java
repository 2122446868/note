package cn.itcast.cas;

import ch.qos.logback.classic.turbo.TurboFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName juc
 * @Package cn.itcast.cas
 * @ClassName CasTest1
 * @Author ZCC
 * @Date 2022/06/02
 * @Description java cas  ģ���û�������վ ͳ���������û������ܴ���(ʹ��synchronized�Ż�)
 * @Version 1.0
 */
@Slf4j(topic = "c.CasTest1")
public class CasTest3 {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    static ReentrantLock lock = new ReentrantLock();

    //���ʴ���
    volatile static  int requestCount = 0;
    //ģ�����һ��

    public static void request() throws InterruptedException {
        //ģ���ʱ5����


        TimeUnit.MILLISECONDS.sleep(5);
        //Ԥ�ڵ�ֵ
        int expectCount;
        do {
            expectCount = getCount();

        } while (!compareAndSwap(expectCount, expectCount + 1));



    }

    /*
    ��ȡ��ǰ���ʴ���
     */
    public static int getCount() {
        return requestCount;
    }

    /**
     * @throws
     * @title compareAndSwap
     * @description
     * @author zcc
     * @param: expectCount  ����count��ֵ
     * @param: newCount     ��Ҫ��count������ֵ
     * @date 2022/6/2 17:22
     * @return: boolean
     */
    public static synchronized boolean compareAndSwap(int expectCount, int newCount) {
        //�ж�count��ǰֵ�Ƿ��������expectCountһ�������һ����newCount��ֵ��count
        if (getCount() == expectCount) {
            requestCount = newCount;
            return true;

        }
        return false;

    }

    public static void main(String[] args) throws InterruptedException {
        long starTime = System.currentTimeMillis();
        // 100���û�ͬʱ������վ ÿ���û�����ʮ��
        int user = 100;
        CountDownLatch countDownLatch = new CountDownLatch(user);
        for (int i = 0; i < user; i++) {
            executorService.execute(() -> {
                //    ģ��һ���û�����10��

                boolean falg = false;
                try {
                    for (int j = 0; j < 10; j++) {

                        request();
                        falg = true;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (falg) {

                        countDownLatch.countDown();
                    }
                }


            });


        }

        countDownLatch.await();
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        log.info(Thread.currentThread().getName() + "����ʱ��" + (endTime - starTime) + ",count=" + requestCount);
    }
}
