package com.shusaku.study.stream;

public class TriFunctionTest {

    static int f1(int i, long l, double d){return 99;}
    public static void main(String[] args){
        /*TriFunction<Integer, Long, Double, Integer> f1 = TriFunctionTest::f1;
        Integer apply = f1.apply(1, 2L, 3.0d);
        System.out.println(apply);
        f1 = (i,l,d) -> 12;
        Integer apply2 = f1.apply(1, 2L, 3.0d);
        System.out.println(apply2);*/
    }

}

/*@FunctionalInterface
interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}*/
