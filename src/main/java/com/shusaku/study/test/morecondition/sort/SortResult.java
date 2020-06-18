package com.shusaku.study.test.morecondition.sort;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortResult {

    public static void main(String[] args) {
        List<SortConditionEntity> list = new ArrayList<>();
        String entities="[{\"year\":\"2017\",\"month\":10,\"count\":47},{\"year\":2017,\"month\":12,\"count\":4},{\"year\":2018,\"month\":11,\"count\":2},{\"year\":2017,\"month\":10,\"count\":2},{\"year\":2016,\"month\":12,\"count\":2},{\"year\":2016,\"month\":12,\"count\":2}]";
        JSONArray array = JSONArray.parseArray(entities);
        array.forEach(entity -> {
            JSONObject json = (JSONObject)entity;
            String year = json.getString("year");
            String month = json.getString("month");
            String count = json.getString("count");
            list.add(new SortConditionEntity(year,month,count));
        });
        list.forEach(entity -> {
            System.out.println(entity.getYear() + " , " + entity.getMonth() + " , " + entity.getCount());
        });

        //自定义排序
        Collections.sort(list, new SortComparator());

        System.out.println("==============");
        list.forEach(entity -> {
            System.out.println(entity.getYear() + " , " + entity.getMonth() + " , " + entity.getCount());
        });

        //java8 sort  默认升序  控制起来不灵活
        List<SortConditionEntity> list2 = list.stream()
                .sorted(Comparator.comparing((SortConditionEntity ::getYear)).reversed()
                .thenComparing(SortConditionEntity::getMonth)//.reversed()
                .thenComparing(SortConditionEntity::getCount).reversed()
                ).collect(Collectors.toList());

        System.out.println("==============");
        list2.forEach(entity -> {
            System.out.println(entity.getYear() + " , " + entity.getMonth() + " , " + entity.getCount());
        });
        System.out.println("==============");
    }

}
