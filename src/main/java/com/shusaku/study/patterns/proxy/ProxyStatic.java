package com.shusaku.study.patterns.proxy;

/**
 * @program: Java8Test
 * @description: 静态代理比较简单，只是在代理对象中，引入了被代理对象  代理对象和被代理对象实现相同的接口  在方法重写的时候  对方法进行增强
 * @author: Shusaku
 * @create: 2019-12-13 11:27
 */
public class ProxyStatic implements Person {

    private Person person;

    public ProxyStatic(Person person) {
        this.person = person;
    }

    @Override
    public void sayHello(String content, int age) {
        System.out.println("ProxyTest sayHello begin");
        //在代理类的方法中 间接访问被代理对象的方法
        person.sayHello(content, age);
        System.out.println("ProxyTest sayHello end");
    }

    @Override
    public void sayGoodBye(boolean seeAgin, double time) {
        System.out.println("ProxyTest sayGoodBye begin");
        //在代理类的方法中 间接访问被代理对象的方法
        person.sayGoodBye(seeAgin, time);
        System.out.println("ProxyTest sayGoodBye end");
    }

    public static void main(String[] args) {
        Student s = new Student();
        ProxyStatic ps = new ProxyStatic(s);
        ps.sayHello("hello! ",20);
        ps.sayGoodBye(true,100);
    }
}
