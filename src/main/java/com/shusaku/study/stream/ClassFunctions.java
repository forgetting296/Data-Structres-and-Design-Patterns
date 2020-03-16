package com.shusaku.study.stream;

import com.google.common.base.Supplier;

import java.util.function.BiConsumer;

public class ClassFunctions {

    static AA f1(){return new AA();}
    static BB f2(){return new BB();}
    static void f4(AA aa, BB bb) {
        System.out.println("f4()");
    }

    public static void main(String[] args){
        Supplier<AA> f1 = ClassFunctions::f1;
        Supplier<BB> f2 = ClassFunctions::f2;
        f1.get();
        f2.get();

        BiConsumer<AA, BB> f4 = ClassFunctions::f4;
        f4.accept(f1.get(),f2.get());
    }

}

class AA {}

class BB {}

class CC {}
