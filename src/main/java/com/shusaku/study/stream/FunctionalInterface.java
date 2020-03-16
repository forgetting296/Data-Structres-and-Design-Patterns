package com.shusaku.study.stream;

import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.function.*;

/**
 * lambda表达式的局部变量默认是被final修饰的
 */
public class FunctionalInterface {

    //支持任意类型的参数  没有返回值
    Consumer<String> con = i -> {
        System.out.println(Integer.parseInt(i));
    };

    //有参数有返回值  泛型中第一个是入参类型  第二个是返回值类型
    Function<String,Integer> func = s -> Integer.valueOf(s);

    //参数是long类型的  基本类型 不是包装类
    LongConsumer lcon = i -> {
        System.out.println(i);
    };

    IntConsumer icon = i -> {
        i ++;
    };

    DoubleConsumer dcon = d -> {
        System.out.println(++ d);
    };

    //两个参数  没有返回值
    BiConsumer<String,Integer> bicon = (s,i) -> {
        System.out.println("String: " + s);
        System.out.println("Integer: " + i);
    };

    //参数类型是long  返回值是double
    LongToDoubleFunction ltdfunc = (l) -> {
        return Double.longBitsToDouble(l);
        //return Double.parseDouble(l + "");
    };

    //入参类型和返回值类型一致
    UnaryOperator unop = s -> s;

    //入参类型和返回值类型都是int
    IntUnaryOperator iunop = i -> i + 1;

    //两个参数  类型相同  都是int
    IntBinaryOperator ibinoper = (i,s) -> s + i;

    //两个参数  类型相同  都是泛型中指定的类型
    BinaryOperator<String> sbinoper = (s1,s2) -> s1 + " " + s2;

    //返回int类型  泛型中的类型  两个参数
    Comparator<Integer> icomp = (i1,i2) -> i1.compareTo(i2);

    //输入泛型中的参数  数量是一个  返回一个布尔值
    Predicate<String> predicate = (s1) -> s1.equals("");
    Predicate<String> predicate2 = (s1) -> s1.equals("aa");

    //没有参数  返回泛型指定的类型
    Supplier<String> supplier = () -> "supplier";
    //Supplier<String> supplier2 = () -> 2;  报错

    public void testIntConsumer(){
//        int i = 0;
//        icon.accept(i);
//        System.out.println(i);
//        double d = 1.0;
//        dcon.accept(d);
//        System.out.println(d);
        bicon.accept("s",1);
        System.out.println(iunop.applyAsInt(1));
        System.out.println(ibinoper.applyAsInt(1,2));
        System.out.println(sbinoper.apply("left","right"));
        System.out.println(icomp.compare(2,3));

        Predicate<String> or = predicate.or(predicate).or(predicate2);
        boolean test = or.test("aab");
        System.out.println(test);
//        System.out.println(predicate.test("aa"));
        System.out.println(supplier.get());
    }

    public void testFunctionNotStatic() {
        com.google.common.base.Function<X, String> f = X::f;
        String apply = f.apply(new X());
        System.out.println(apply);

        Consumer<X> ff = X::ff;
        ff.accept(new X());

        Maps.EntryTransformer<X, String, String> fff = X::fff;
        System.out.println(fff.transformEntry(new X(),"X::fff"));

        com.google.common.base.Function<String, String> sf = X::sf;
        String apply1 = sf.apply("X static function sf(String)");
        System.out.println(apply1);

        Multi multi = X::sf2;
        String apply2 = multi.apply("a", "b", "c");
        System.out.println(apply2);


    }

}

class X{
    String f(){
        return "X::f()";
    }
    void ff(){
        System.out.println("X::ff()");
    }

    String fff(String s){
        return s;
    }

    static String sf(String s){
        return s;
    }
    static String sf2(String s,String s2,String s3){
        return s + s2 + s3;
    }
    String sf3(String s,int s2){
        return s + s2;
    }
}

interface Multi{
    String apply(String s, String s2, String s3);
}
