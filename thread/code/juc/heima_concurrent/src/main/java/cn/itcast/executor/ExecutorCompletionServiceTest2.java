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
 * @Description ExecutorCompletionService  执行一批任务 然后消费结果
 * @Version 1.0
 */
@Slf4j(topic = "c.ExecutorCompletionServiceTest2")
public class ExecutorCompletionServiceTest2 {
    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Callable<Integer>> list = new ArrayList<>();
        int taskCount = 5;
        for (int i = taskCount; i > 0; i--) {
            int j = i * 2;
            list.add(() -> {
                TimeUnit.SECONDS.sleep(j);
                return j;
            });
        }
        solve(executorService, list, a -> {
           log.info(System.currentTimeMillis() + ":" + a);
        });
        executorService.shutdown();

    }


    public static <T> void solve(Executor executor, Collection<Callable<T>> solvers, Consumer<T> use) throws InterruptedException, ExecutionException {
        if (CollectionUtils.isEmpty(solvers)) {
            return;
        }
        ExecutorCompletionService<T> executorCompletionService = new ExecutorCompletionService<>(executor);
        for (Callable callable : solvers) {
            executorCompletionService.submit(callable);
        }

        int j = solvers.size();
        for (int i = 0; i < j; i++) {
            T t = executorCompletionService.take().get();
            if (t!=null){
                use.accept(t);
            }
        }

    }
}

