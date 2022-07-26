package mengbai.study.stack;

/**
 * @ProjectName jvm
 * @Package PACKAGE_NAME
 * @ClassName StackFarmeTest1
 * @Author ZCC
 * @Date 2022/07/21
 * @Description TODO
 * @Version 1.0
 */
public class StackFarmeTest1 {
    public static void main(String[] args) {
        StackFarmeTest1 stackFarmeTest1 = new StackFarmeTest1();

        System.out.println("main  开始");
        stackFarmeTest1.methhod1();
        System.out.println("main  结束");

    }


    public void methhod1() {
        System.out.println("mehtod1开始");
        method2();

        System.out.println("mehtod1结束");
    }

    public void method2() {
        System.out.println("mehtod2开始");
        method3();

        System.out.println("mehtod2结束");

    }

    public void method3() {
        System.out.println("mehtod3开始");
        int i = 0;
        int b = i + 10;
        System.out.println("mehtod3结束");

    }
}
