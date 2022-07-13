package cn.itcast.reentranLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName ReentranLockTest1
 * @Author ZCC
 * @Date 2022/05/30
 * @Description ø…÷ÿ»ÎÀ¯
 * @Version 1.0
 */
@Slf4j(topic = "c.ReentranLockTest1")
public class ReentranLockTest1 {
    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        try {
            lock.lock();
            log.info("into main");
            m1();
        } finally {
            lock.unlock();

        }

    }

    public static void m1() {

        try {
            lock.lock();
            log.info("into m1");
            m2();
        } finally {
            lock.unlock();


        }
    }

    public static void m2() {

        try {
            lock.lock();
            log.info("into m2");

        } finally {
            lock.unlock();


        }
    }
}