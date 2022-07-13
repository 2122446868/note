package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName PriorityBlockingQueueTest
 * @Author ZCC
 * @Date 2022/06/01
 * @Description PriorityBlockingQueue 线程优先级队列测试
 * @Version 1.0
 */
@Slf4j(topic = "c.PriorityBlockingQueueTest")
public class PriorityBlockingQueueTest {
    static ExecutorService executor = new ThreadPoolExecutor(
            1,
            1,
            60L, TimeUnit.SECONDS,
            new PriorityBlockingQueue());

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            String taskName = "任务" + i;
            executor.execute(new Task(i, taskName));
        }
        for (int i = 100; i >= 90; i--) {
            String taskName = "任务" + i;
            executor.execute(new Task(i, taskName));
        }
        executor.shutdown();
    }
}






@Slf4j(topic = "c.Task")
class Task implements Runnable, Comparable<Task> {

    private int i;
    private String name;

    public Task(int i, String name) {
        this.i = i;
        this.name = name;
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(o.i, this.i);
    }

    @Override
    public void run() {
        log.info(Thread.currentThread().getName() + "处理" + this.name);
    }
}