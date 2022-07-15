package com.mengbai.study;

/**
 * @ProjectName jvm
 * @Package com.mengbai.study
 * @ClassName ClassLoadTest1
 * @Author ZCC
 * @Date 2022/07/13
 * @Description 类加载器
 * @Version 1.0
 */
public class ClassLoadTest1 {
    public static void main(String[] args) {
        //sun.misc.Launcher$AppClassLoader@18b4aac2   系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);

        //sun.misc.Launcher$ExtClassLoader@4554617c   扩展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);

        //null BootStrap ClassLoad 引导类加载器是c实现的无法获取到
        ClassLoader bootStrapClassLoader = extClassLoader.getParent();
        System.out.println(bootStrapClassLoader);

        //获取当前自定义类的加载器   sun.misc.Launcher$AppClassLoader@18b4aac2
        ClassLoader classLoader = ClassLoadTest1.class.getClassLoader();
        System.out.println(classLoader);

        //获取String类的加载器是空的 说明java的核心类库是引导类加载器加载的
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);


    }
}
