package cn.itcast.test;

import cn.itcast.n2.util.Downloader;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName Test13
 * @Author ZCC
 * @Date 2022/04/05
 * @Description 设计模式--保护性暂停
 * @Version 1.0
 */
@Slf4j(topic = "c.Test13")
public class Test13 {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 3; i++) {
            new People().start();
        }

        TimeUnit.SECONDS.sleep(1);

        for (Integer id : Mobile.getIds()) {
            new Mail(id, "内容" + id).start();

        }

    }

}

@Slf4j(topic = "c.People")
class People extends Thread {
    @Override
    public void run() {
        GuardedObjectT guardedObjectT = Mobile.createGuardedObjectT();
        log.info("开始收信 id:{}", guardedObjectT.getId());
        Object o = guardedObjectT.get(5000);
        log.info("收到信 id:{},内容：{}", guardedObjectT.getId(), o.toString());

    }
}

@Slf4j(topic = "c.Mail")
class Mail extends Thread {
    private int id;
    private String mail;

    public Mail(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObjectT guardedObjectT = Mobile.getGuardedObjectT(id);
        log.info("送信 id：{},内容：{}", guardedObjectT.getId(), mail);
        guardedObjectT.complate(mail);

    }
}

class Mobile {
    private static Map<Integer, GuardedObjectT> map = new Hashtable<>();
    private static int id = 1;

    /***
     * @title getId
     * @description 产生唯一ID
     * @author zcc
     * @date 2022/4/5 13:29
     * @return: int
     * @throws
     */
    private static synchronized int getId() {
        return id++;
    }

    public static GuardedObjectT getGuardedObjectT(int id) {
        return map.remove(id);

    }

    public static GuardedObjectT createGuardedObjectT() {
        GuardedObjectT guardedObjectT = new GuardedObjectT(getId());
        map.put(guardedObjectT.getId(), guardedObjectT);
        return guardedObjectT;
    }

    public static Set<Integer> getIds() {
        return map.keySet();
    }
}

class GuardedObjectT {
    private int id;
    //结果
    private Object response;

    public GuardedObjectT(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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