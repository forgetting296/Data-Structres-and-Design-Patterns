package com.shusaku.study.patterns.singleton;

import java.util.stream.IntStream;

public class SingletonTest {

    private static final class ResourceImpl implements Resource {

        private int i;

        public ResourceImpl(int j) {
            this.i = j;
        }

        @Override
        public synchronized int getValue() {
            return i;
        }

        @Override
        public synchronized void setValue(int x) {
            this.i = x;
        }

        private static class ResourceHolder {
            private static ResourceImpl resource = new ResourceImpl(17);
        }

        public static ResourceImpl getResource() {
            return ResourceHolder.resource;
        }

    }

    //@Test
    public void test() {
        Resource resource = ResourceImpl.getResource();
        System.out.println(resource.getValue());
        Resource resource1 = ResourceImpl.getResource();
        resource1.setValue(22);
        System.out.println(resource.getValue());
        IntStream
                .range(0,10)
                .filter(i -> i %2 == 0)
                .forEach(i -> System.out.println(i));
    }

    private SingletonTest() {

    }

    private static final SingletonTest st = new SingletonTest();

    //无锁线程安全的单例模式  免去了检查instance == null　的校验　　避免使用同步锁机制
    public synchronized static SingletonTest getInstance() {
        return st;
    }

    private static SingletonTest instance = null;

    //效率低　　采用同步锁机制
    /*public synchronized static SingletonTest getInstance2() {
        if(instance == null) {
            instance = new SingletonTest();
        }
        return instance;
    }*/
    public static SingletonTest getInstance2() {

        synchronized (SingletonTest.class) {
            if(instance == null) {
                instance = new SingletonTest();
            }
        }

        return instance;
    }

    //双重校验同步锁机制  只有在ｉｎｓｔａｎｃｅ未被实例化的时候　才会被同步锁锁住　　但是这种方式　instance对象必须加volatile关键字　保证线程之间的可见性和禁止指令重排序
    public static SingletonTest getInstance3() {

        if(instance == null) {
            synchronized (SingletonTest.class) {
                if(instance == null) {
                    instance = new SingletonTest();
                }
            }
        }

        return instance;
    }






}

interface Resource {
    int getValue();
    void setValue(int x);
}
