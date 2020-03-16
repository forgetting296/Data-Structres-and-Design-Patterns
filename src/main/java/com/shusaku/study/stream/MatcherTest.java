package com.shusaku.study.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatcherTest {

    public void show(Matcher matcher,Integer i){
        System.out.println(
                matcher.test(IntStream.rangeClosed(1,9)
                .boxed()
                .peek(n -> System.out.format("%d ", n)),
                        n -> n < i)
        );
    }

    public void test(){
        show(Stream ::allMatch,10);
        show(Stream ::anyMatch,0);
        show(Stream ::allMatch,4);
        show(Stream ::anyMatch,7);
        show(Stream :: noneMatch, 0);
        show(Stream :: noneMatch, 9);
    }

    public void testNumeric(){
        System.out.println(RandInts.rands()
                //.peek(n -> System.out.format("%d ",n))
                .average()
                .getAsDouble());

        Map<String,String> map = new HashMap<>();
        for(Map.Entry<String,String> entry : map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " -- " + value);
        }
    }

}

interface Matcher extends BiPredicate<Stream<Integer>, Predicate<Integer>>{}
