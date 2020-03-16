package com.shusaku.study.stream;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CreatingOptionals {

    public void test(String testName, Optional<String> opt){
        System.out.println("=== " + testName + " ===");
        System.out.println(opt.orElse("Null"));
    }

    String[] elements = {
            "Foo", "", "Bar", "Baz", "Bingo","12"
    };

    public void testFilter(String testName, Predicate<String> opt){
        System.out.println("=== " + testName + " ===");
        for(int i = 0,j = elements.length;i < j;i ++){
            System.out.println(Arrays.stream(elements)
                                .skip(i)   //跳过第i个 从0开始  之后findFirst  现象就是顺序获取
                                .findFirst().filter(opt));
        }
    }

    public void testMap(String testName, Function<String,String> func){
        System.out.println("=== " + testName + " ===");
        for(int i = 0,j = elements.length;i <= j;i ++){
            System.out.println(Arrays.stream(elements)
                    .skip(i)   //跳过第i个 从0开始  之后findFirst  现象就是顺序获取
                    .findFirst()
                    .map(func));
        }
    }

    public void testFlatMap(String testName, Function<String,Optional<String>> func){
        System.out.println("=== " + testName + " ===");
        for(int i = 0,j = elements.length;i <= j;i ++){
            System.out.println(Arrays.stream(elements)
                    .skip(i)   //跳过第i个 从0开始  之后findFirst  现象就是顺序获取
                    .findFirst().flatMap(func));
        }
    }


    /**
     * 在自己的代码中使用Optional可以使用下边三个静态方法
     * empty()          生成一个空的Optional
     * of(value)        将一个非空的值包装到Optional中
     * ofNullable(value)针对一个可能为空的值，为空时自动生成Optional.empty()，否则将值包装在Optional中
     */
    public void testOptional(){
        //在Optional中 empty null 和空字符串 是三种不同的东西
        test("empty",Optional.empty());
        test("of",Optional.of("a"));
        try {
            test("of",Optional.of(null));
        }catch (Exception e){
            System.out.println(e);
        }
        test("ofNullable",Optional.ofNullable(null));
        test("ofNullable",Optional.ofNullable("Hi"));
        test("ofNullable",Optional.ofNullable(""));
    }

    public void testArraysToString(){
        String[] strs = {"a","B","c"};
        System.out.println(strs);
        System.out.println(Arrays.toString(strs));
        Integer i = 1;
        System.out.println(i.equals(null));
        System.out.println("a" + String.format("%n","b"));
    }

    public void testNullList(){
        ArrayList<Object> list = Lists.newArrayList();
        List<Object> list2 = list.stream().collect(Collectors.toList());
        System.out.println(list2);
        Map<String,String> map = new HashMap<>();
        List<String> collect = map.keySet().stream().collect(Collectors.toList());
        System.out.println(collect);
        Integer i = 1;
        System.out.println(Arrays.equals(new int[]{1},new int[]{2}));
        System.out.println(i.equals(null));
    }

    /**
     * 使用管道流生成了Optional对象
     * filter():将Predicate用于Optional中的内容并返回结果，如果Optional不满足Predicate()时返回空，如果Optional为空，直接返回
     * map(Function):如果Optional不为空，应用Function在Optional中的内容，并返回结果，否则直接返回Optional.empty()
     * flatMap(Function)：同map()，但是提供的映射函数将结果包装在Optional对象中，因此，flatMap()不会在最后进行任何包装
     *
     */
    public void testOptional2(){

        testFilter("true",str -> true);
        testFilter("false",str -> false);
        testFilter("str != \"\"",str -> !"".equals(str));

    }

    public void testOptional3(){

        testMap("Add brackets", (s) -> "【" + s + "】");
        testMap("Increment", s -> {
            try{
                return Integer.parseInt(s) + 1 + "";
            }catch (Exception e){
                return s;
            }
        });
    }

    public void testOptional4(){
        testFlatMap("Add brackets", s -> {
            return Optional.ofNullable(s);
        });

        testFlatMap("Increment", s -> {
            try {
                return Optional.ofNullable(Integer.parseInt(s) + 1 + "");
            }catch (Exception e){
                return Optional.ofNullable(s);
            }
        });
    }

    /**
     * 使用Optional这个流的时候，要进行解包才能拿到流中的元素
     * 一般都使用filter(Optional::isPresent)先进行非空过滤
     * 再使用map(Optional::get)进行元素的获取
     * 这只是一般的情况  不同的应用程序定义的条件不同、定义的空值也不同  需要采用不同的行为
     */


    /**
     * 对每个元素应用最终操作
     * forEach(Consumer)
     * forEachOrdered(Consumer) 这个形式保证了forEach的操作顺序是原始流顺序
     *
     * 保证forEach的顺序只有在引入parallel()（并发）操作时才有意义  可以在并发时  保证数据输出的顺序
     */
    public void testForEach(){
        int size = 10;
        RandInts randInts = new RandInts();
        RandInts.rands().limit(size).forEach(s -> System.out.format("%d ",s));
        System.out.println();
        RandInts.rands().limit(size).parallel().forEach(s -> System.out.format("%d ",s));
        System.out.println();
        RandInts.rands().limit(size).parallel().forEachOrdered(s -> System.out.format("%d ",s));
    }

    public void testOptional5() {
        //List<String> list = null;
        List<String> list = Lists.newArrayList("a","b");
        try {
            Optional.ofNullable(list).orElseThrow(() -> {return new RuntimeException("null");});
        }catch (Exception e) {
            e.printStackTrace();
        }

        Optional.ofNullable(list).ifPresent(ls -> {
            System.out.println(ls);
        });
    }

    public void testException() {
        try {
            throw new RuntimeException("test throw");
        }catch (Exception e) {
            System.out.println("catch success into catch: " + e.getMessage());
            //e.printStackTrace();
        }
    }




}

class RandInts {
    private static int[] rints = new Random(47).ints(0, 1000).limit(100).toArray();
    public static IntStream rands() {
        return Arrays.stream(rints);
    }
}
