package com.shusaku.study.test.morecondition.sort;

import java.util.Comparator;

public class SortComparator implements Comparator<SortConditionEntity> {

    @Override
    public int compare(SortConditionEntity o1, SortConditionEntity o2) {
        int cr = 0;

        int a = Integer.parseInt(o1.getYear()) - Integer.parseInt(o2.getYear());

        if(a != 0) {
            cr = (a < 0) ? 3 : -1;     // "<" 降序   ">" 升序
        } else { //year相等 比较month
            a = Integer.parseInt(o1.getMonth()) - Integer.parseInt(o2.getMonth());
            if(a != 0) {
                cr = (a < 0) ? 2 : -2;    // "<" 降序   ">" 升序
            } else {
                a = Integer.parseInt(o1.getCount()) - Integer.parseInt(o2.getCount());
                if(a != 0) {
                    cr = (a < 0) ? 1 : -3;    // "<" 降序   ">" 升序
                }
            }
        }
        return cr;
    }
}
