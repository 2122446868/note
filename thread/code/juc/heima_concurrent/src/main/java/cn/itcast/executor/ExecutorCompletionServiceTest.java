package cn.itcast.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName ExecutorCompletionServiceTest
 * @Author ZCC
 * @Date 2022/06/02
 * @Description ExecutorCompletionService  ��ȡ���߳����������ɵ��߳�������
 * @Version 1.0
 */
@Slf4j(topic = "c.ExecutorCompletionServiceTest")
public class ExecutorCompletionServiceTest {

    @Slf4j(topic = "c.GoodsModel")
    static class GoodsModel {
        //��Ʒ����
        String name;
        //���￪ʼʱ��
        long startime;
        //�͵���ʱ��
        long endtime;

        public GoodsModel(String name, long startime, long endtime) {
            this.name = name;
            this.startime = startime;
            this.endtime = endtime;
        }

        @Override
        public String toString() {
            return name + "���µ�ʱ��[" + this.startime + "," + endtime + "]����ʱ:" + (this.endtime - this.startime);
        }

    }

    /**
     * ����Ʒ����¥
     *
     * @param goodsModel
     * @throws InterruptedException
     */
    static void moveUp(GoodsModel goodsModel) throws InterruptedException {
        //����5�룬ģ�����¥��ʱ
        TimeUnit.SECONDS.sleep(5);
        log.info("����Ʒ����¥����Ʒ��Ϣ:" + goodsModel);
    }

    /**
     * ģ���µ�
     *
     * @param name     ��Ʒ����
     * @param costTime ��ʱ
     * @return
     */
    static Callable<GoodsModel> buyGoods(String name, long costTime) {
        return () -> {
            long startTime = System.currentTimeMillis();
            log.info(startTime + "����" + name + "�µ�!");
            //ģ���ͻ���ʱ
            try {
                TimeUnit.SECONDS.sleep(costTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            log.info(endTime + name + "�͵���!");
            return new GoodsModel(name, startTime, endTime);
        };
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long st = System.currentTimeMillis();
        log.info(st + "��ʼ����!");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //����ExecutorCompletionService����
        ExecutorCompletionService<GoodsModel> executorCompletionService = new ExecutorCompletionService<>(executor);
        //�첽�µ��������
        executorCompletionService.submit(buyGoods("����", 5));
        //�첽�µ�����ϴ�»�
        executorCompletionService.submit(buyGoods("ϴ�»�", 2));
        executor.shutdown();
        //������Ʒ������
        int goodsCount = 2;
        for (int i = 0; i < goodsCount; i++) {
            //���Ի�ȡ�����ȵ�����Ʒ
            GoodsModel goodsModel = executorCompletionService.take().get();
            //�����ȵ�����Ʒ����¥
            moveUp(goodsModel);
        }
        long et = System.currentTimeMillis();
        log.info(et + "�������͵����￩����������");
        log.info("�ܺ�ʱ:" + (et - st));

    }
}

