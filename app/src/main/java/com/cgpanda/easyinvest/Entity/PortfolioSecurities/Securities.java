package com.cgpanda.easyinvest.Entity.PortfolioSecurities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Securities {

    @SerializedName("secid")
    @Expose
    private String secid;

    @SerializedName("boardid")
    @Expose
    private String boardId;

    @SerializedName("market")
    @Expose
    private String market;

    @SerializedName("securities")
    @Expose
    private List<PortfolioSecuritiesStaticData> portfolioSecuritiesStaticData;

    @SerializedName("marketdata")
    @Expose
    private List<PortfolioSecuritiesMarketData> portfolioSecuritiesMarketData;


    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }


    public List<PortfolioSecuritiesStaticData> getPortfolioSecuritiesStaticData() {
        return portfolioSecuritiesStaticData;
    }

    public void setPortfolioSecuritiesStaticData(List<PortfolioSecuritiesStaticData> portfolioSecuritiesStaticData) {
        this.portfolioSecuritiesStaticData = portfolioSecuritiesStaticData;
    }

    public List<PortfolioSecuritiesMarketData> getPortfolioSecuritiesMarketData() {
        return portfolioSecuritiesMarketData;
    }

    public void setPortfolioSecuritiesMarketData(List<PortfolioSecuritiesMarketData> portfolioSecuritiesMarketData) {
        this.portfolioSecuritiesMarketData = portfolioSecuritiesMarketData;
    }
}
