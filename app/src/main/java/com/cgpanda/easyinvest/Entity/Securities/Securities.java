package com.cgpanda.easyinvest.Entity.Securities;

public class Securities {
    public String name;
    public String shortName;
    public double currentPrice;
    public double lastPrice;
    public InstrumentTypes instrumentType;

    public Securities(String name, String shortName, double currentPrice, double lastPrice, InstrumentTypes instrumentType) {
        this.name = name;
        this.shortName = shortName;
        this.currentPrice = currentPrice;
        this.lastPrice = lastPrice;
        this.instrumentType = instrumentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }
}
