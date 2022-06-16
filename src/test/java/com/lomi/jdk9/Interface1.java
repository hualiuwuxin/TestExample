package com.lomi.jdk9;

/**
 * @author ZHANGYUKUN
 * @date 2022/6/16
 */
public interface Interface1 {

    /**
     * 正常抽象方法
     */
    void methodNotmal();

    /**
     * java9才有的私有方法(只能自己调用，自理不能调用)
     */
    private  void methodProvide() {
        System.out.println("methodProvide");
    }

    /**
     * java8才有的default 方法
     */
    default void methodDefault() {
        System.out.println("methodProvide");
        methodProvide();
    }

    /**
     * java8 才有的 static 方法
     */
    static void methodStatic() {
        System.out.println("methodProvide");
    }


}
