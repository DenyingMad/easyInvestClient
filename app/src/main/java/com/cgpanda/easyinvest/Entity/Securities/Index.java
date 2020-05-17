package com.cgpanda.easyinvest.Entity.Securities;

public class Index extends Securities {
    public Index(String name, String shortName, double currentPrice, double lastPrice, InstrumentTypes instrumentType) {
        super(name, shortName, currentPrice, lastPrice, instrumentType);
    }
}
