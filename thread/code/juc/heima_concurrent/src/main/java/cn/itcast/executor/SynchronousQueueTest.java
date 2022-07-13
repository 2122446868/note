package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName SynchronousQueueTest
 * @Author ZCC
 * @Date 2022/06/01
 * @Description SynchronousQueue ͬ�����в���
 * @Version 1.0
 */
@Slf4j(topic = "c.SynchronousQueueTest")
public class SynchronousQueueTest {
    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            int j = i;
            String taskName = "����" + j;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "����" + taskName);
                //ģ�������ڲ������ʱ
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
