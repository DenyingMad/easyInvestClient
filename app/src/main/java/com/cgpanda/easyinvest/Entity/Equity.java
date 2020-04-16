package com.cgpanda.easyinvest.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Equity {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("shortName")
    @Expose
    private String shortName;

    @SerializedName("codeName")
    @Expose
    private String codeName;

    @SerializedName("price")
    @Expose
    private double price = 200;

    @SerializedName("closePrice")
    @Expose
    private double closePrice = 100;

    private double dynamicValue = closePrice - price;
    private double dynamicPercent = 100;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getDynamicValue() {
        return dynamicValue;
    }


    public double getDynamicPercent() {
        dynamicPercent = (price / closePrice - 1) * 100;



        return new BigDecimal(dynamicPercent).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }


    public Equity(long id, String shortName, String codeName) {
        this.id = id;
        this.shortName = shortName;
        this.codeName = codeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String shortName) {
        this.codeName = shortName;
    }
}
