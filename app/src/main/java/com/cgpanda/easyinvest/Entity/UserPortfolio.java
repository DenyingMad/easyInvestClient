package com.cgpanda.easyinvest.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPortfolio {

    public UserPortfolio(){}

    // Конструктор для пустого портфеля
    public UserPortfolio(long id){
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("currentPrice")
    @Expose
    private double currentPrice;

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
