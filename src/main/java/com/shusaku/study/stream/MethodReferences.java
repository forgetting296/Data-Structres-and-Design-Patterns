package com.shusaku.study.stream;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.function.Consumer;

public class MethodReferences {

    static void hello(String name){
        System.out.println("hello " + name);
    }

    static class Description{
        String about;
        Description(String desc){about = desc;}
        void help(String msg){
            System.out.println(about + " " + msg);
        }
    }

    static class Helper{
        static void assist(String msg){
            System.out.println(msg);
        }
    }

    String func(){
        return "MethodReferences :: func";
    }

    String func2(String s){
        return s;
    }

    void func3(){
        System.out.println("fun3");
    }

    public static void main(String[] args){
        Describe d = new Describe();
        Consumer<String> show = d::show;
        show.accept("aaa");

        Description desc = new Description("Jack");
        Consumer<String> help = desc::help;
        help.accept("read me");

        MethodReferences mr = new MethodReferences();
        Callable call = MethodReferences::hello;
        Consumer<String> hello = MethodReferences::hello;
        call.call("Jekins");
        hello.accept("J");
        System.out.println(ImmutableMap.of("k1","v1","k2","v2"));

        //类的非静态方法  使用类名::方法名  表示未绑定的方法引用 在调用的时候需要传入对应的实例化之后的对象
        //泛型中是方法名和返回值
        Function<MethodReferences, String> func = MethodReferences::func;
        String apply = func.apply(mr);
        System.out.println(apply);

        Maps.EntryTransformer<MethodReferences, String, String> func2 = MethodReferences::func2;
        String s = func2.transformEntry(mr, "wocao  我真牛逼！");
        System.out.println(s);

        Consumer<MethodReferences> func3 = MethodReferences::func3;
        func3.accept(mr);
    }
}

interface Callable{
    void call(String s);
}

class Describe{
    void show(String massage){
        System.out.println(massage);
    }
}


