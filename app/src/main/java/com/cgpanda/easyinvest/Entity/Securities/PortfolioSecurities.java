package com.cgpanda.easyinvest.Entity.Securities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PortfolioSecurities {

    @SerializedName("securities")
    @Expose
    private Securities securities;

    @SerializedName("amount")
    @Expose
    private int amount;

    private double currentPrice = 10.2d;
    private double lastPrice = 8.8d;
    private String shortName = "def";

    public Securities getSecurities() {
        return securities;
    }

    public void setSecurities(Securities securities) {
        this.securities = securities;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
