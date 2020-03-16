package com.shusaku.study.patterns.adaptor;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 15:48
 */
public class Wire {

    private String name;
    private WireCap left;
    private WireCap right;

    public Wire(String name) {
        this.name = name;
        this.left = new WireCap(this);
        this.right = new WireCap(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WireCap getLeft() {
        return left;
    }

    public void setLeft(WireCap left) {
        this.left = left;
    }

    public WireCap getRight() {
        return right;
    }

    public void setRight(WireCap right) {
        this.right = right;
    }

    public void link(Wire link) {
        left.addLinkTo(link.getRight());
        link.getRight().addLinkTo(left);
    }


}
