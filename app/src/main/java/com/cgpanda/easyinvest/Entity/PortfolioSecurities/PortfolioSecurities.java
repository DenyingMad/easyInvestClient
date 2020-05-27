package com.cgpanda.easyinvest.Entity.PortfolioSecurities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PortfolioSecurities {

    @SerializedName("securities")
    @Expose
    private Securities securities;

    @SerializedName("amount")
    @Expose
    private int amount;

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

}
