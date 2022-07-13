package cn.itcast.countDownLatchT;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName CountDownLatchTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description 使用CountDownLatch 封装一个并行处理工具类
 * @Version 1.0
 */
@Slf4j(topic = "c.CountDownLatchTest4")
public class CountDownLatchTest4 {

    public static void main(String[] args) throws InterruptedException {
        //生成1-10的10个数字，放在list中，相当于10个任务
        List<Integer> list = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());
        //启动多线程处理list中的数据，每个任务休眠时间为list中的数值
        TaskDisposeUtils.dispose(list, item -> {
            try {
                long startTime = System.currentTimeMillis();
                TimeUnit.SECONDS.sleep(item);
                long endTime = System.currentTimeMillis();
                System.out.println(System.currentTimeMillis() + ",任务" + item + "执行完毕，耗时:" + (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //上面所有任务处理完毕完毕之后，程序才能继续
        System.out.println(list + "中的任务都处理完毕!");
    }


}


class TaskDisposeUtils {
    private static final int POOL_SIZE;

    static {
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }


    /**
     * 并行处理，并等待结束
     *
     * @param taskList 任务列表
     * @param consumer 消费者
     * @param <T>
     * @throws InterruptedException
     */
    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) throws InterruptedException {
        dispose(true, POOL_SIZE, taskList, consumer);
    }


    /**
     * 并行处理，并等待结束
     *
     * @param moreThread 是否多线程执行
     * @param poolSize   线程池大小
     * @param taskList   任务列表
     * @param consumer   消费者
     * @param <T>
     * @throws InterruptedException
     */
    public static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) throws InterruptedException {
        if (CollectionUtils.isEmpty(taskList)) {
            return;

        }
        if (moreThread && poolSize > 0) {
            poolSize = Math.min(poolSize, taskList.size());
            ExecutorService executorService = null;

            try {
                executorService = Executors.newFixedThreadPool(taskList.size());
                final CountDownLatch countDownLatch = new CountDownLatch(taskList.size());
                for (T item : taskList) {
                    executorService.execute(() -> {
                        try {

                            consumer.accept(item);
                        } finally {

                            countDownLatch.countDown();


                        }

                    });

                }
                countDownLatch.await();

            } finally {
                if (executorService != null) {
                    executorService.shutdown();

                }
            }

        } else {
            for (T item : taskList) {
                consumer.accept(item);
            }
        }

    }

}
