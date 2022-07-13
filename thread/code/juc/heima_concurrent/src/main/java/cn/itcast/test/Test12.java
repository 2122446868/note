package cn.itcast.test;

import cn.itcast.n2.util.Downloader;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName Test12
 * @Author ZCC
 * @Date 2022/04/05
 * @Description 设计模式--保护性暂停
 * @Version 1.0
 */
@Slf4j(topic = "c.Test12")
public class Test12 {
    public static void main(String[] args) {
        GuardedObject obj = new GuardedObject();
        new Thread(() -> {

            log.info("等待结果");
            List<String> res = (List<String>) obj.get(2000);
            log.info("结果大小：{}", res.size());

        }, "t1").start();


        new Thread(() -> {
            try {
                log.info("结果下载");
                TimeUnit.SECONDS.sleep(2);
                List<String> downloadeRes = Downloader.download();
                obj.complate(downloadeRes);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }


        }, "t2").start();
    }
}

class GuardedObject {
    //结果
    private Object response;


    /***
     * @title get
     * @description 获取结果   增加超时
     * @author zcc
     * @date 2022/4/5 10:26
     * @return: java.lang.Object
     * @throws
     */
    public Object get(long timeout) {
        synchronized (this) {
            //防止虚假唤醒
            //开始时间
            long begin = System.currentTimeMillis();
            //经历时间
            long experience = 0;
            while (response == null) {

                long wait = timeout - experience;
                if (wait <= 0) {
                    break;
                }
                try {
                    //wait 防止虚假唤醒重新计算等待时间
                    // this.wait(wait);
                    this.wait(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                experience = System.currentTimeMillis() - begin;

            }
            return this.response;

        }
    }

    /***
     * @title complate
     * @description 产生结果
     * @author zcc
     * @date 2022/4/5 10:28
     * @throws
     */
    public void complate(Object response) {
        synchronized (this) {
            this.response = response;
            //唤醒所有线程
            this.notifyAll();
        }

    }

}