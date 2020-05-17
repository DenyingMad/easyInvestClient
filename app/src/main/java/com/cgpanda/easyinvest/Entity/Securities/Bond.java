package com.cgpanda.easyinvest.Entity.Securities;

public class Bond extends Securities {
    public Bond(String name, String shortName, double currentPrice, double lastPrice, InstrumentTypes instrumentType) {
        super(name, shortName, currentPrice, lastPrice, instrumentType);
    }
}
