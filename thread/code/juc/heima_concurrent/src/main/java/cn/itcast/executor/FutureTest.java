package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName FutureTest
 * @Author ZCC
 * @Date 2022/06/02
 * @Description ��ȡ�첽����ִ�н�� Future
 * @Version 1.0
 */
@Slf4j(topic = "c.FutureTest")
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
         ExecutorService executors = Executors.newFixedThreadPool(10);

        Future<Integer> submit = executors.submit(() -> {
            log.info(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start!");
            TimeUnit.SECONDS.sleep(5);
            log.info(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end!");

            return 10;

        });
        log.info(System.currentTimeMillis() + "," + Thread.currentThread().getName());
       log.info(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",�����" + submit.get(2,TimeUnit.SECONDS));
       executors.shutdown();
    }

}
