package com.shusaku.study.stream;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectTest {

    public void testCollectToMap(){
        /*RandomPair randomPair = new RandomPair();
        Iterator<Character> capChars = randomPair.capChars;
        while(capChars.hasNext()){
            System.out.println(capChars.next());
        }*/
        //Map<Integer, Character> map = new RandomPair().stream().limit(4).collect(Collectors.toMap(Pair::getI, Pair::getC));
        //使用Collectors.toMap()时，要保证key不重复
        Map<Character, Integer> map = new RandomPair().stream().limit(4).collect(Collectors.toMap(Pair::getC, Pair::getI));
        System.out.println(map);
    }

    public void test(){
        String[] pipFile = new String[]{};
        //String[] pipFile = null;
        for(String s : pipFile){
            System.out.println(s);
        }

        System.out.println((pipFile != null && pipFile.length > 0) ? pipFile[0] : null);
    }

}

class Pair {
    public final Character c;
    public final Integer i;
    Pair(Character c, Integer i) {
        this.c = c;
        this.i = i;
    }
    public Character getC() { return c; }
    public Integer getI() { return i; }
    @Override
    public String toString() {
        return "Pair(" + c + ", " + i + ")";
    }
}

class RandomPair {
    Random rand = new Random();
    // An infinite iterator of random capital letters:
    Iterator<Character> capChars = rand.ints(65,91)
            //.distinct()
            .peek(System.out::println)
            .mapToObj(i -> (char)i)
            .iterator();
    public Stream<Pair> stream() {
        return rand.ints(100, 1000).distinct()
                .mapToObj(i -> new Pair(capChars.next(), i));
    }
}
