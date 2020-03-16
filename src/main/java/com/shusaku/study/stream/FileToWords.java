package com.shusaku.study.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileToWords {

    private String content;

    public FileToWords (String filePath) throws IOException {
        content = Files.lines(Paths.get(filePath)).collect(Collectors.joining(" "));
    }

    public Stream<String> stream(){
        return Pattern.compile("[ .,?]+ ").splitAsStream(content);
    }

    public static void main(String[] args){

    }
}
