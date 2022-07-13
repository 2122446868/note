package cn.itcast.test;

import cn.itcast.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName Test10
 * @Author ZCC
 * @Date 2022/03/22
 * @Description TODO
 * @Version 1.0
 */
@Slf4j(topic = "c.Test10")
public class Test10 {
    private static Integer r = 0;


    public static void main(String[] args) throws InterruptedException {
        test1();
    }


    private static void test1() throws InterruptedException {
        {
            log.info("开始");
            Thread t1 = new Thread(() -> {
                log.info("开始");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("结束");

                r = 10;

            }, "t1");

            t1.start();

            //等待t1运行结束
            t1.join();
            log.info("结果为:{}", r);
            log.info("结束");
        }
    }
}
