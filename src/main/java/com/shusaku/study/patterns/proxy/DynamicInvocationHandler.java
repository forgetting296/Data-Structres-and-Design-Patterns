package com.shusaku.study.patterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @program: Java8Test
 * @description: 动态代理
 * @author: Shusaku
 * @create: 2019-12-13 11:37
 */
public class DynamicInvocationHandler implements InvocationHandler{

    private Object object;

    public DynamicInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("DynamicInvocationHandler invoke begin!");
        System.out.println("proxy: " + proxy.getClass().getName());
        System.out.println("method: " + method.getName());

        Arrays.stream(args).forEach(arg -> {
            System.out.println("arg: " + arg);
        });
        method.invoke(object,args);
        System.out.println("DynamicInvocationHandler invoke end!");
        return null;
    }

    public static void main(String[] args) {
        Student student = new Student();
        //获取类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        DynamicInvocationHandler dih = new DynamicInvocationHandler(student);
        Class<?>[] interfaces = student.getClass().getInterfaces();
        Person o = (Person)Proxy.newProxyInstance(classLoader, interfaces, dih);
        o.sayHello("hello!",17);
        o.sayGoodBye(true,77777);
        System.out.println("end");
    }
}
