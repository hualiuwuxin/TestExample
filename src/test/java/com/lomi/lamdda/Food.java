package com.lomi.lamdda;

/**
 * 描述
 *
 * @Author ZHANGYUKUN
 * @Date 2022/6/13
 */
public interface Food {

    void eat();

    default void deat1(){
        System.out.println("deat1");
    };
    default void deat2(){
        System.out.println("deat2");
    };
    static void seat1(){
        System.out.println("seat1");
    };
    static void seat2(){
        System.out.println("seat2");
    };


}
