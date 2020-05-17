package com.cgpanda.easyinvest.Entity.Securities;

// Обыкновенная акция
public class CommonStock extends Securities{
    public CommonStock(String name, String shortName, double currentPrice, double lastPrice, InstrumentTypes instrumentType) {
        super(name, shortName, currentPrice, lastPrice, instrumentType);
    }
}
