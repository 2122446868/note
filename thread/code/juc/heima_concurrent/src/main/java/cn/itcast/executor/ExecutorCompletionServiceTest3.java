package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName ExecutorCompletionServiceTest
 * @Author ZCC
 * @Date 2022/06/02
 * @Description ExecutorCompletionService 异步执行一批任务，有一个完成立即返回，其他取消
 * @Version 1.0
 */
@Slf4j(topic = "c.ExecutorCompletionServiceTest3")
public class ExecutorCompletionServiceTest3 {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startime = System.currentTimeMillis();
        List<Callable<Integer>> list = new ArrayList<>();
        int taskCount = 5;
        for (int i = taskCount; i > 0; i--) {
            int j = i * 2;
            list.add(() -> {
                log.info(Thread.currentThread().getName()+"start");
                TimeUnit.SECONDS.sleep(j);
                log.info(Thread.currentThread().getName()+"end :" +j);
                return j;
            });

        }
        Integer integer = invokeAny(executorService, list);
       log.info("耗时:" + (System.currentTimeMillis() - startime) + ",执行结果:" + integer);

        executorService.shutdown();

    }

    public static <T> T invokeAny(Executor e, Collection<Callable<T>> solvers) {
        if (CollectionUtils.isEmpty(solvers)) {
            return null;
        }
        ExecutorCompletionService<T> executorCompletionService = new ExecutorCompletionService<>(e);
        List<Future<T>> futureList = new ArrayList<>();
        for (Callable callable : solvers) {
            futureList.add(executorCompletionService.submit(callable));

        }
        int j = solvers.size();

        for (int i = 0; i < j; i++) {
            try {
                T t = executorCompletionService.take().get();
                if (t != null) {
                    return t;

                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            } finally {
                // 取消其它任务
                for (Future<T> future : futureList) {
                    future.cancel(true);

                }

            }
        }

        return null;

    }


}

