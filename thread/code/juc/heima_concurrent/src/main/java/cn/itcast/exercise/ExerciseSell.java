package cn.itcast.exercise;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @ProjectName juc
 * @Package cn.itcast.exercise
 * @ClassName exerciseSell
 * @Author ZCC
 * @Date 2022/04/02
 * @Description 多线程模拟抢票
 * @Version 1.0
 */
@Slf4j(topic = "c.ExerciseSell")
public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        //模拟售票窗口
        TicketWindow ticketWindow = new TicketWindow(1000);
        //统计售票数
        List<Integer> integers = new Vector<Integer>();
        //线程数量
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i <= 20000; i++) {
            Thread thread = new Thread(() -> {
                int count = ticketWindow.sell(randomAmout());
                integers.add(count);
            }, i + "");
            threadList.add(thread);
            thread.start();
        }

        //等待所有线程执行完成
        for (Thread thread : threadList) {
            thread.join();
        }

        log.info("剩余票：{}", ticketWindow.getCount());
        log.info("售票数量：{}", integers.stream().mapToInt(a -> a).sum());
    }

    static Random random = new Random();

    public static int randomAmout() {
        return random.nextInt(5) + 1;
    }


}


class TicketWindow {
    private  int count;

    public TicketWindow(int count) {
        this.count = count;

    }

    public int getCount() {
        return this.count;
    }

    public synchronized int sell(int amount) {
        if (amount < +this.count) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }

}
