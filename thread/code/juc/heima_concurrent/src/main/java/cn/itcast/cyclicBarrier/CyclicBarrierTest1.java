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
 * @Description CyclicBarrier��ʹ��
 * @Version 1.0
 */
@Slf4j(topic = "c.CyclicBarrierTest1")
public class CyclicBarrierTest1 {
    public static void main(String[] args) throws InterruptedException {
        /**
         * ��������˾��֯���Σ���Ҷ��о�������10���ˣ����絽�����ˣ���Ҫ�ȵ�10���˶����˲��ܿ������ȵ��������ǵ���
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

        for (int i = 0; i < 11; i++) {
            new Thread(() -> {
                long starTime = System.currentTimeMillis();
                //    ģ������
                try {
                    cyclicBarrier.await();
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                log.info(Thread.currentThread().getName() + ",sleep:" + 2 + " �ȴ���" + (System.currentTimeMillis() - starTime) + "(ms),��ʼ�Է��ˣ�");
            }).start();


        }

    }
}