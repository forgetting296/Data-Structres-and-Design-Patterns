package com.shusaku.study.leetcode;

import java.util.*;

/**
 * @author liuzi
 */
public class ComputeAdjacentWords {

    /**
     * 给出包含一些单词作为关键词和只在一个字母上不同的一列单词作为关键字的值
     * 输出那些具有minWords个或更多个通过1字母替换得到的单词的单词
     * @param adjWords
     * @param minWords
     */
    public static void printHighChangeables(Map<String, List<String>> adjWords, int minWords){
        for(Map.Entry<String,List<String>> entry:adjWords.entrySet()){
            List<String> words = entry.getValue();
            if(words.size() >= minWords){
                System.out.print(entry.getKey() + "(");
                System.out.print(words.size() + "):");
                for(String word : words){
                    System.out.print(" " + word);
                }
                System.out.println();
            }
        }
    }

    public static boolean oneCharOff(String word1,String word2){
        if(word1.length() != word2.length()){
            return false;
        }
        int diffs = 0;
        for(int i = 0,j = word1.length();i < j;i ++){
            if(word1.charAt(i) != word2.charAt(i)){
                if(++ diffs > 1){
                    return false;
                }
            }
        }
        return diffs == 1;
    }

    /**
     * 这种方式效率很低，最直观的问题是  会比对长度不同的单词
     * @param theWords
     * @return
     */
    public static Map<String,List<String>> computeAdjacentWords(List<String> theWords){
        Map<String,List<String>> map = new TreeMap<>();
        for(int i = 0;i < theWords.size();i ++){
            for(int j = i + 1;j < theWords.size();j ++){
                if(oneCharOff(theWords.get(i),theWords.get(j))){
                    update(map,theWords.get(i),theWords.get(j));
                    update(map,theWords.get(j),theWords.get(i));
                }
            }
        }
        return map;
    }


    public static Map<String,List<String>> computeAdjacentWords2(List<String> theWords){
        Map<String,List<String>> map = new TreeMap<>();
        Map<Integer,List<String>> wordByLength = new TreeMap<>();
        //字母长度相同的放在一个List中  同一个长度的进行比较
        for(String w : theWords){
            update(wordByLength,w.length(),w);
        }
        for(List<String> words : wordByLength.values()){
            for(int i = 0;i < words.size();i ++){
                for(int j = i + 1;j < words.size();j ++){
                    if(oneCharOff(words.get(i),words.get(j))){
                        update(map,words.get(i),words.get(j));
                        update(map,words.get(j),words.get(i));
                    }
                }
            }
        }
        return map;
    }

    /**
     * 效率最高
     * @param theWords
     * @return
     */
    public static Map<String,List<String>> computeAdjacentWords3(List<String> theWords){
        Map<String,List<String>> map = new HashMap<>();
        Map<Integer,List<String>> wordByLength = new HashMap<>();

        for(String w:theWords){
            update(wordByLength,w.length(),w);
        }

        for(Map.Entry<Integer,List<String>> entry : wordByLength.entrySet()){
            Integer groupNum = entry.getKey();
            List<String> groupWords = entry.getValue();
            for(int i = 0;i < groupNum;i ++){
                Map<String,List<String>> repToWord = new HashMap<>();
                //这个groupWords中的单词是长度都相同的
                for(String str:groupWords){
                    //将索引为i位置上的字符切掉  切掉后的字符串作为key  切之前的字符串存到value中
                    String rep = str.substring(0,i) + str.substring(i + 1);
                    //key是相同的  才会都进到同一个value对应的List
                    update(repToWord,rep,str);
                }
                //这个repToWord 的value  集合中存储的都是 去掉同一个位置字符之前的单词  而且长度相同  这个集合中的数据进行update校验
                for(List<String> words : repToWord.values()){
                    //一个key  对应的value如果大于1 说明有跟它只差一个字母的其他单词
                    if(words.size() >= 2){
                        for(String s1 : words){
                            for(String s2 : words){
                                if(!s1.equals(s2)){
                                    update(map,s1,s2);
                                }
                            }
                        }
                    }
                }
            }
        }

        return map;
    }

    private static <T> void update(Map<T,List<String>> map,T t,String word){
        List<String> values = map.get(t);
        if(values == null){
            values = new ArrayList<>();
            map.put(t,values);
        }
        values.add(word);
    }


}
