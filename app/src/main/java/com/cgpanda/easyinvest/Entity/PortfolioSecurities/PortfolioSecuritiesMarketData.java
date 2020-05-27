package com.cgpanda.easyinvest.Entity.PortfolioSecurities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class PortfolioSecuritiesMarketData {
    @SerializedName("SECID")
    @Expose
    private String secid;

    @SerializedName("BOARDID")
    @Expose
    private String boardid;

    @SerializedName("OPEN")
    @Expose
    private BigDecimal open;

    @SerializedName("LOW")
    @Expose
    private BigDecimal low;

    @SerializedName("HIGH")
    @Expose
    private BigDecimal high;

    @SerializedName("LAST")
    @Expose
    private BigDecimal last;


    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }
}