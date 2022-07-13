package cn.itcast.semajphorre;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName SemaphoreTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description Semaphore信号量
 * @Version 1.0
 */
@Slf4j(topic = "c.SemaphoreTest1")
public class SemaphoreTest1 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        for (int i=0;i<10;i++){
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.info("当前线程；"+Thread.currentThread().getName()+",获取许可证");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    log.info("当前线程；"+Thread.currentThread().getName()+",释放许可证");
                }


            }).start();
        }


    }
}
