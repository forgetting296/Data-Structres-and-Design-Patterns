package com.shusaku.study.patterns.adaptor;

/**
 * @program: Java8Test
 * @description:
 * @author: Shusaku
 * @create: 2020-03-02 15:48
 */
public class WireCap {

    public static WireCap LooseCap = new WireCap(null);
    private Wire wire;
    WireCap link = WireCap.LooseCap;

    public WireCap(Wire wire) {
        this.wire = wire;
    }

    public void addLinkTo(WireCap link) {
        this.link = link;
    }

    public WireCap getLink() {
        return link;
    }

    public Wire getWire() {
        return wire;
    }

    @Override
    public String toString() {

        if(WireCap.LooseCap.equals(link)) {
            return "WireCap belonging to LooseCap";
        }
        return "WireCap belonging to " + wire + " is linked to " + link.getWire();

    }
}
