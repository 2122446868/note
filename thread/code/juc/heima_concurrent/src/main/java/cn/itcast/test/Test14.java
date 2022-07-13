package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName Test14
 * @Author ZCC
 * @Date 2022/04/05
 * @Description 设计模式 生产者消费者
 * @Version 1.0
 */
@Slf4j(topic = "c.Test14")
public class Test14 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {

                messageQueue.put(new Message(id, id + "消息生产"));
            }).start();
        }


        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    messageQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

/***
 * @title
 * @description 消息队列类 Java线程之间的通信
 * @author zcc
 * @date 2022/4/5 16:11
 * @throws
 */
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {
    //消息的队列集合
    private LinkedList<Message> queue = new LinkedList<>();
    //容量
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    /**
     * @throws
     * @title take
     * @description 获取消息
     * @author zcc
     * @date 2022/4/5 16:12
     * @return: cn.itcast.test.Message
     */
    public Message take() {
        synchronized (queue) {
            log.info("获取消息" + Thread.currentThread().getName());
            while (queue.isEmpty()) {
                try {
                    log.info("没有消息，等待" + Thread.currentThread().getName());
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            log.info("消费消息" + Thread.currentThread().getName());
            Message mes = queue.removeFirst();
            queue.notifyAll();
            return mes;


        }


    }

    /**
     * @throws
     * @title put
     * @description 存入消息
     * @author zcc
     * @param: mes
     * @date 2022/4/5 16:12
     */
    public void put(Message mes) {
        synchronized (queue) {
            log.info("存入消息" + Thread.currentThread().getName());
            while (queue.size() == capcity) {
                try {
                    log.info("消息队列已满" + Thread.currentThread().getName());
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            queue.addLast(mes);
            queue.notifyAll();
            log.info("消息存入成功" + Thread.currentThread().getName());


        }

    }

}


final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}