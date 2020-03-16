package com.shusaku.study.leetcode;

/**
 * @program: Java8Test
 * @description: 穷举所有的可能性，从杯子里面水变化角度来讲，每个状态到下一个状态只有6种行为：3L杯子装满，5L杯子装满，3L倒空，5L倒空，3L倒到5L，5L倒到3L。
 *              最终结果如果5L的里面有4L水则得到一个解。如果当前状态和前面一个状态重复，则取消继续往后的探索。
 * @author: Shusaku
 * @create: 2020-01-15 10:48
 */
public class Bucket3AndBucket5 {

    private static Status[] steps = new Status[100];

    public static void main(String[] args){
        steps[0] = new Status();
        solve(1);
    }

    private static void solve(final int depth){
        //paint(depth-1);
        if(duplicate(depth - 1)){
            return;
        }
        if(steps[depth - 1].cup5 == 4){
            paint(depth - 1);
            return;
        }

        Status preStatus = steps[depth - 1];

        steps[depth] = preStatus.full3();
        solve(depth + 1);

        steps[depth] = preStatus.full5();
        solve(depth + 1);

        steps[depth] = preStatus.trans3to5();
        solve(depth + 1);

        steps[depth] = preStatus.trans5to3();
        solve(depth + 1);

        steps[depth] = preStatus.empty5();
        solve(depth + 1);

        steps[depth] = preStatus.empty3();
        solve(depth + 1);

    }

    private static boolean duplicate(int depth){
        for(int i=0 ; i < depth ; i++){
            if( steps[i].equals(steps[depth])){
                return true;
            }
        }
        return false;
    }

    private static void paint(int depth){
        for(int i = 0; i <= depth; i++){
            System.out.print(steps[i]+" ");
        }
        System.out.println();
    }

    static class Status{
        int cup3;
        int cup5;

        public Status(){

        }

        public Status(Status s){
            this.cup3 = s.cup3;
            this.cup5 = s.cup5;
        }

        public Status full3(){
            Status newStatus = new Status(this);
            newStatus.cup3 = 3;
            return newStatus;
        }

        public Status full5(){
            Status newStatus = new Status(this);
            newStatus.cup5 = 5;
            return newStatus;
        }

        public Status trans3to5(){
            Status newStatus = new Status(this);
            if(5 - newStatus.cup5 >= newStatus.cup3){
                newStatus.cup5 += newStatus.cup3;
                newStatus.cup3 = 0;
            }else{
                newStatus.cup3 -= (5 - newStatus.cup5);
                newStatus.cup5 = 5;
            }
            return newStatus;
        }

        public Status trans5to3(){
            Status newStatus = new Status(this);
            if(newStatus.cup5 <= 3 - newStatus.cup3){
                newStatus.cup3 += newStatus.cup5;
                newStatus.cup5 = 0;
            }else{
                newStatus.cup5 -= (3 - newStatus.cup3);
                newStatus.cup3 = 3;
            }
            return newStatus;
        }

        public Status empty3(){
            Status newStatus = new Status(this);
            newStatus.cup3 = 0;
            return newStatus;
        }

        public Status empty5(){
            Status newStatus = new Status(this);
            newStatus.cup5 = 0;
            return newStatus;
        }

        public String toString(){
            return "["+this.cup3+"#"+this.cup5+"]";
        }

        public boolean equals(Object o){
            if(o == null){
                return false;
            }
            if(o == this){
                return true;
            }
            if(o instanceof Status){
                Status s = (Status)o;
                return s.cup3 == this.cup3 && s.cup5 == this.cup5;
            }
            return false;
        }
    }

}
