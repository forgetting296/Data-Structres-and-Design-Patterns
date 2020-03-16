package com.shusaku.study.stream;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalBasics {

    static void test(Optional<String> optString){
        if(optString.isPresent())
            System.out.println(optString.get());
        else
            System.out.println("Nothing inside!");
    }

    static void ifPresent(Optional<String> optString){
        optString.ifPresent(System.out::println);
    }

    static void orElse(Optional<String> optString){
        System.out.println(optString.orElse("Nada"));
    }

    static void orElseGet(Optional<String> optString){
        System.out.println(optString.orElseGet(() -> "Generated"));
    }

    static void orElseThrow(Optional<String> optString) {
        try {
            System.out.println(optString.orElseThrow(
                    () -> new Exception("Supplied")));
        }
        catch(Exception e) {
            System.out.println("Caught " + e);
        }
    }

    static void test(String testName, Consumer<Optional<String>> cos){
        System.out.println("=== " + testName + " ===");
        //Stream.of("Epithets").findFirst()返回流  Epithets是泛型对应的数据类型  对应输出的内容  执行对应的静态方法
        cos.accept(Stream.of("Epithets").findFirst());
        cos.accept(Stream.<String>empty().findFirst());
    }

    public void test() {
        test(Stream.of("test").findFirst());
        test(Stream.<String>empty().findFirst());
        System.out.println("=====================");
        //
        test("ifPresent",OptionalBasics::ifPresent);
        test("orElse",OptionalBasics::orElse);
        test("orElseGet",OptionalBasics::orElseGet);
        test("orElseThrow",OptionalBasics::orElseThrow);
        System.out.println("=====================");
    }

    public void testOptional1() {
        List<String> list = Lists.newArrayList("a","b",null,"c");
        List<String> list1 = Lists.newArrayList();
        List<String> list2 = null;
        Optional.ofNullable(list2)
                .ifPresent(ls -> {
                    ls.stream()
                            .filter(s -> Optional.ofNullable(s).isPresent())
                            .forEach(s -> {
                                System.out.println(s);
                            });
                });
    }

    public void testFiles() throws IOException {

        String path = "/home/liuziping/桌面/渊婷/sati/借记卡知识导出/toJson/process/tojokaki/json/nodeResult/node.json";
        List<String> collect = Files.lines(Paths.get(path), Charset.forName("utf-8"))
                .flatMap(line -> Arrays.stream(line.split("\n")))
                .collect(Collectors.toList());
        Optional.ofNullable(collect)
                .ifPresent(list -> {
                    list.stream()
                            .filter(s -> Optional.ofNullable(s).isPresent())
                            .forEach(s -> {
                                System.out.println(s);
                            });
                });
    }

    public void testDirectory() {

        String dirPath = "/home/liuziping/桌面/渊婷/sati/借记卡知识导出/toJson/process/json23/know_ledge";
        File trainDirFile = new File(dirPath);
        File[] files1 = trainDirFile.listFiles();
        List<File> files = Arrays.asList(files1);
        Optional.ofNullable(files)
                .ifPresent(files2 -> {
                    files.stream()
                            .filter(file -> Optional.ofNullable(file).isPresent())
                            .forEach(file -> {
                                try {
                                    System.out.println(file.getName());
                                    List<String> strings = Files.readAllLines(Paths.get(file.getAbsolutePath()));
                                    Optional.ofNullable(strings)
                                            .ifPresent(strings2 -> {
                                                strings2.stream()
                                                        .filter(str -> Optional.ofNullable(str).isPresent())
                                                        .forEach(s -> {
                                                            System.out.println(s);
                                                        });
                                            });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                });
    }

}
