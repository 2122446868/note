package cn.itcast.test;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName Test11
 * @Author ZCC
 * @Date 2022/03/31
 * @Description TODO
 * @Version 1.0
 */
@Slf4j(topic = "c.Test11")
public class Test11 {
    private static boolean  running = true;

    private static void run() {
        System.out.println("1");
        // log.info("1");
        while (running) {

        }
        // log.info("2");
        System.out.println("2");
    }

    public static void main(String[] args) throws InterruptedException {
        // new Thread(Test11::run,"t1").start();
        // TimeUnit.SECONDS.sleep(1);
        // running = false;
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
