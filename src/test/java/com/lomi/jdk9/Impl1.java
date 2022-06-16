package com.lomi.jdk9;

/**
 * @author ZHANGYUKUN
 * @date 2022/6/16
 */
public class Impl1 implements Interface1 {


    @Override
    public void methodNotmal() {
        System.out.println("正常方法必须被重写");
    }

    @Override
    public void methodDefault() {
        System.out.println("默认方法可以不重写");
    }


}
