package com.shusaku.study.stream;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FunctionComposition {

    static Function<String,String>
        f1 = s -> {
            System.out.println(s);
            return s.replace("A","_");
        },
        f2 = s -> s.substring(3),
        f3 = s -> s.toLowerCase(),
        f4 = f1.compose(f2).andThen(f3);


    public void test(){
        String s = f4.apply("GO AFTER ALL AMBULANCES");
        System.out.println(s);
    }

    static Predicate<String>
        p1 = s -> s.contains("bar"),
        p2 = s -> s.length() < 5,
        p3 = s -> s.contains("foo"),
        p4 = p1.negate().and(p2).or(p3);//negate()表示取反

    public void testPredicate(){
        Stream.of("foobar","foobaz","fongopuckey")
                .filter(p4)//filter()中进行复杂的逻辑判断
                .forEach(System.out::println);
    }

    Function<String,Function<String,String>> add = a -> b -> a + b;

    public void testCurrying(){
        Function<String, String> hi_ = add.apply("hi ");
        String tony = hi_.apply("tony");
        System.out.println(tony);
    }

    Function<String,Function<String,Function<String,String>>> fff = a -> b -> c -> a + b + c;

    public void testCurry3Args(){
        String apply = fff.apply("hi ").apply("sanji ").apply("hhh");
        System.out.println(apply);
    }

    //处理基本类型和装箱时，可以适当使用Function接口
    IntFunction<IntUnaryOperator> curriedIntAdd = a -> b -> a + b;

    public void testIntFunction(){
        IntUnaryOperator apply = curriedIntAdd.apply(1);
        int i = apply.applyAsInt(1);
        System.out.println(i);
    }
}
