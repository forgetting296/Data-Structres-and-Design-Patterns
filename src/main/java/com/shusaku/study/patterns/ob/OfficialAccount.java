package com.shusaku.study.patterns.ob;

import java.util.Observable;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 11:41
 */
public class OfficialAccount extends Observable {

    public void publishNewInfo(String info) {

        //标示这个Ｏｂｓｅｒｖａｂｌｅ对象已经改变了，　更具体的是把Observable中的属性changed改为true
        setChanged();

        //再通知所有观察者之前　　需要判断Observable中的属性changed是否为true  如果不是　　不会发出这个通知
        //具体可以看源码
        notifyObservers(info);
    }

    public static void main(String[] args) {
        OfficialAccount account = new OfficialAccount();

        User bob = new User("Bob");
        User tom = new User("Tom");
        User me = new User("Me");

        //将观察者注册到观察对象的观察者列表中
        account.addObserver(bob);
        account.addObserver(tom);
        account.addObserver(me);

        //发布消息
        account.publishNewInfo("...新内容...");
        account.deleteObserver(me);

        System.out.println("##########################");
        account.publishNewInfo("...新内容１２３...");

    }

}
