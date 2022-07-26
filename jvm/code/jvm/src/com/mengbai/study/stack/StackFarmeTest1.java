package com.mengbai.study.stack;

/**
 * @ProjectName jvm
 * @Package com.mengbai.study.stack
 * @ClassName StackFarmeTest1
 * @Author ZCC
 * @Date 2022/07/24
 * @Description TODO
 * @Version 1.0
 */
public class StackFarmeTest1 {
    public static void main(String[] args) {
        StackFarmeTest1 stackFarmeTest1 = new StackFarmeTest1();
        stackFarmeTest1.method1();

    }


    public void method1() {
        System.out.println("method1 start");
        method2();
        System.out.println("method1 end");
    }

    public void method2() {
        System.out.println("method2 start");
        method3();
        System.out.println("method2 end");
    }


    public void method3() {
        System.out.println("method3 start");

        int i = 0;
        int b = i + 10;
        System.out.println("method执行了一些运算！");
        System.out.println("method3 end");
    }
}
