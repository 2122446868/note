package mengbai.study.stack;

import java.util.Date;

/**
 * @ProjectName jvm
 * @Package mengbai.study.stack
 * @ClassName LocalVariableTest
 * @Author ZCC
 * @Date 2022/07/25
 * @Description 局部变量表
 * @Version 1.0
 */
public class LocalVariableTest {
    private Integer count = 1;

    public static void main(String[] args) {
        LocalVariableTest localVariableTest = new LocalVariableTest();
        int num = 1;
        localVariableTest.method1();
        System.out.println("end:" + num);


    }

    public void method1() {
        Date date = new Date();
        String s = "atguigu";
        System.out.println(s + "：" + date);

    }

    public void method2() {
        System.out.println(this.count);

    }

    public static void method3() {
        long l = 123L;
        double d = 2.0;

    }


    /**
     * @throws
     * @title method4
     * @description slot 重复利用
     * @author zcc
     * @date 2022/7/26 15:19
     */
    public void method4() {
        int a = 0;
        {
            int b = 2;
            a =b+a;

        }
        // C会重复利用B的槽位
        int c = 3;
    }
}
