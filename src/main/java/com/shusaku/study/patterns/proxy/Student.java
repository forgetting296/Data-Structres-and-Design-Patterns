package com.shusaku.study.patterns.proxy;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2019-12-13 11:22
 */
public class Student implements Person {
    @Override
    public void sayHello(String content, int age) {
        System.out.println("student say hello" + content + " "+ age);
    }

    @Override
    public void sayGoodBye(boolean seeAgin, double time) {
        System.out.println("student sayGoodBye " + time + " "+ seeAgin);
    }
}
