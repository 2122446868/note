package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName CustomRejectedExecutionHandler
 * @Author ZCC
 * @Date 2022/06/01
 * @Description �Զ��履�Ͳ��� ��Ҫʵ��RejectedExecutionHandler�ӿ�
 * @Version 1.0
 */
@Slf4j(topic = "c.CustomRejectedExecutionHandler")
public class CustomRejectedExecutionHandler {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            5,
            5,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(1),
            Executors.defaultThreadFactory(),
            (r, executors) -> {
                //�Զ��履�Ͳ���
                //��¼һ���޷����������
                log.info("�޷����������" + r.toString());
            });

    public static void main(String[] args) {
        for (int i = 0; i <= 11; i++) {
            executor.execute(() -> {
                log.info(Thread.currentThread().getName() + "����");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }

        executor.shutdown();

    }

}
