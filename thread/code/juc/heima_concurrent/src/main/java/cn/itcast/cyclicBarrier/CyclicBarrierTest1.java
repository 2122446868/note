package cn.itcast.cyclicBarrier;

import ch.qos.logback.core.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName CyclicBarrierTest1
 * @Author ZCC
 * @Date 2022/05/31
 * @Description CyclicBarrier简单使用
 * @Version 1.0
 */
@Slf4j(topic = "c.CyclicBarrierTest1")
public class CyclicBarrierTest1 {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 场景；公司组织旅游，大家都有经历过，10个人，中午到饭点了，需要等到10个人都到了才能开饭，先到的人坐那等着
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

        for (int i = 0; i < 11; i++) {
            new Thread(() -> {
                long starTime = System.currentTimeMillis();
                //    模拟休眠
                try {
                    cyclicBarrier.await();
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                log.info(Thread.currentThread().getName() + ",sleep:" + 2 + " 等待了" + (System.currentTimeMillis() - starTime) + "(ms),开始吃饭了！");
            }).start();


        }

    }
}