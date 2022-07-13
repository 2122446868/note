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
 * @Description ʹ��CountDownLatch ��װһ�����д�������
 * @Version 1.0
 */
@Slf4j(topic = "c.CountDownLatchTest4")
public class CountDownLatchTest4 {

    public static void main(String[] args) throws InterruptedException {
        //����1-10��10�����֣�����list�У��൱��10������
        List<Integer> list = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());
        //�������̴߳���list�е����ݣ�ÿ����������ʱ��Ϊlist�е���ֵ
        TaskDisposeUtils.dispose(list, item -> {
            try {
                long startTime = System.currentTimeMillis();
                TimeUnit.SECONDS.sleep(item);
                long endTime = System.currentTimeMillis();
                System.out.println(System.currentTimeMillis() + ",����" + item + "ִ����ϣ���ʱ:" + (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //��������������������֮�󣬳�����ܼ���
        System.out.println(list + "�е����񶼴������!");
    }


}


class TaskDisposeUtils {
    private static final int POOL_SIZE;

    static {
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }


    /**
     * ���д������ȴ�����
     *
     * @param taskList �����б�
     * @param consumer ������
     * @param <T>
     * @throws InterruptedException
     */
    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) throws InterruptedException {
        dispose(true, POOL_SIZE, taskList, consumer);
    }


    /**
     * ���д������ȴ�����
     *
     * @param moreThread �Ƿ���߳�ִ��
     * @param poolSize   �̳߳ش�С
     * @param taskList   �����б�
     * @param consumer   ������
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
