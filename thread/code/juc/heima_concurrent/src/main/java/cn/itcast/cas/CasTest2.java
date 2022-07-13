package cn.itcast.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.cas
 * @ClassName CasTest1
 * @Author ZCC
 * @Date 2022/06/02
 * @Description java cas  ģ���û�������վ ͳ���������û������ܴ���(ʹ��synchronized)
 * @Version 1.0
 */
@Slf4j(topic = "c.CasTest1")
public class CasTest2 {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    //���ʴ���
    static   int requestCount = 0;
    //ģ�����һ��

    public static synchronized void  request() throws InterruptedException {
        //ģ���ʱ5����


        TimeUnit.MILLISECONDS.sleep(5);
        requestCount++;


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
