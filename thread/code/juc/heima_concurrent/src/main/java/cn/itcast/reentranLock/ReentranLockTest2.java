package cn.itcast.reentranLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName juc
 * @Package cn.itcast.test
 * @ClassName ReentranLockTest1
 * @Author ZCC
 * @Date 2022/05/30
 * @Description �ɴ����
 * @Version 1.0
 */
@Slf4j(topic = "c.ReentranLockTest2")
public class ReentranLockTest2 {
    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            //�ɴ����
            //���û�о�����ô�η�����ȡlock������
            //����о����ͽ����������У����Ա�����������interrupt�������
            log.debug("��ȡ��");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("�����");
                //���֮������ִ��
                return;
            }

            try {
                log.info("��ȡ����");

            } finally {
                lock.unlock();
            }

        }, "t1");

        lock.lock();
        thread.start();

        Thread.sleep(1000);
        //���
        thread.interrupt();


    }
}