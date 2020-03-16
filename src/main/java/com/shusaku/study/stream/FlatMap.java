package com.shusaku.study.stream;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlatMap {

    static Random rand = new Random(47);

    public static void main(String[] args){
        //flatMap()  获取流产生函数  并将其应用到新的元素  然后获取每一个流将其“扁平”为元素
        Stream.of(1,2,3)
                .flatMap(i -> Stream.of("A","B","C"))
                .forEach(System.out::println);

        //引入一个concat 以参数顺序组合两个流  在每个随机生层的流末尾添加一个-1 作为标记
        //因为rand.ints 产生的是一个IntStream 所以必须使用flatMap()
        Stream.of(1,2,3,4,5)
                .flatMapToInt(i -> IntStream.concat(rand.ints(0,100).limit(i),IntStream.of(-1))).forEach(System.out::println);
    }

}
