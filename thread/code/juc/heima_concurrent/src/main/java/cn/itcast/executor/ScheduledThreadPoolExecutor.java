package cn.itcast.executor;

import javafx.scene.input.DataFormat;
import jdk.internal.dynalink.beans.StaticClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.executor
 * @ClassName ScheduledThreadPoolExecutor
 * @Author ZCC
 * @Date 2022/06/01
 * @Description ��ʱ�����߳�
 * @Version 1.0
 */
@Slf4j(topic = "c.ExtendExecutuor")
public class ScheduledThreadPoolExecutor {
   static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info(dateTimeFormatter.format(now));
        scheduledExecutorService.schedule(()->{
            log.info(System.currentTimeMillis() + "��ʼִ��");
            //ģ�������ʱ
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(System.currentTimeMillis() + "ִ�н���");

        },3,TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();
    }
}
