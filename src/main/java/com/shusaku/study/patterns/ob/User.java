package com.shusaku.study.patterns.ob;

import java.util.Observable;
import java.util.Observer;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 11:35
 */
public class User implements Observer {

    private String name;

    public User(String name) {
        this.name = name;
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println("微信用户：　" + name + " 您的微信公众号更新了如下内容: " + arg);
    }
}
