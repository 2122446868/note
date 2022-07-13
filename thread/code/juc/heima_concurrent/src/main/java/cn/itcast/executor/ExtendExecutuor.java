package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName ExtendEXecuteros
 * @Author ZCC
 * @Date 2022/06/01
 * @Description �̳߳���չ
 * @Version 1.0
 */
@Slf4j(topic = "c.ExtendExecutuor")
public class ExtendExecutuor {
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
            }) {
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println(System.currentTimeMillis() + "," + t.getName() + ",��ʼִ������:" + r.toString());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",����:" + r.toString() + "��ִ�����!");
        }

        @Override
        protected void terminated() {
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "���ر��̳߳�!");
        }
    };

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            String taskName = "����-" + i;
            executor.execute(() -> {
                log.info(Thread.currentThread().getName() + "����" + taskName);
            });
        }
        TimeUnit.SECONDS.sleep(1);
        executor.shutdown();

        System.out.println(Runtime.getRuntime().availableProcessors());

    }
}
