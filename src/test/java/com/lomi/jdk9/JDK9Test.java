package com.lomi.jdk9;

import org.junit.Test;

/**
 * jdk9 测试
 *
 * @author ZHANGYUKUN
 * @date 2022/6/16
 */
public class JDK9Test {


    @Test
    public void t1(){
        Impl1 impl1 = new Impl1();
        Impl2 impl2 = new Impl2();

        impl1.methodDefault();
        impl1.methodNotmal();

        //接口的静态方法只能通过接口名字调用，不能通过实例对象调用
        Interface1.methodStatic();





    }




}
