package cn.itcast.exercise;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.Random;

/**
 * @ProjectName juc
 * @Package cn.itcast.exercise
 * @ClassName ExerciseTran
 * @Author ZCC
 * @Date 2022/04/02
 * @Description 多线程模拟抢票
 * @Version 1.0
 */@Slf4j(topic = "c.ExerciseTransfer")
public class ExerciseTransfer {
    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i <= 1000; i++) {
                account1.transf(account2, randomAmout());
            }
        }, "t1");


        Thread thread2 = new Thread(() -> {
            for (int i = 0; i <= 1000; i++) {
                account2.transf(account1, randomAmout());
            }
        }, "t2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        log.info("total:{}",account1.getMoney()+account2.getMoney());
    }


    static Random random = new Random();

    public static int randomAmout() {
        return random.nextInt(5) + 1;
    }

}

class Account {
    private Integer money;

    public Account(int money) {
        this.money = money;

    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    //转账
    public  void transf(Account targer, Integer transferMoney) {
        synchronized (Account.class){
        if (this.money >= transferMoney) {
            this.setMoney(getMoney() - transferMoney);
            targer.setMoney(targer.getMoney() + transferMoney);


        }
        }
    }

}