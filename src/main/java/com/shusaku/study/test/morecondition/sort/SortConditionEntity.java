package com.shusaku.study.test.morecondition.sort;

public class SortConditionEntity {

    private String year;
    private String month;
    private String count;

    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }

    //构造方法
    public SortConditionEntity(String year, String month, String count) {
        this.year = year;
        this.month = month;
        this.count = count;
    }
    //默认构造方法
    public SortConditionEntity() {

    }

}
