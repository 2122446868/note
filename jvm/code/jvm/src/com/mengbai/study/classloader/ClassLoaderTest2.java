package com.mengbai.study;

import com.oracle.net.Sdp;
import sun.misc.Launcher;

import java.net.URL;

/**
 * @ProjectName jvm
 * @Package com.mengbai.study
 * @ClassName ClassLoaderTest2
 * @Author ZCC
 * @Date 2022/07/13
 * @Description 类加载器
 * @Version 1.0
 */
public class ClassLoaderTest2 {
    public static void main(String[] args) {
        System.out.println("======bootstrap classLoader 引导类加载器======");
        //获取引导类加载器能加载的类路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }
        //获取应到类加载器路径下 所属类的加载类    null
        ClassLoader classLoader = Sdp.class.getClassLoader();
        System.out.println(classLoader);

        System.out.println("======bootstrap classLoader 扩展类加载器======");
        String property = System.getProperty("java.ext.dirs");
        for (String url : property.split(";")) {
            System.out.println(url);

        }



    }
}
