package com.cgpanda.easyinvest.Entity;

import com.cgpanda.easyinvest.Entity.PortfolioSecurities.PortfolioSecurities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPortfolio {

    public UserPortfolio(long id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("securitiesPortfolioSet")
    @Expose
    private List<PortfolioSecurities> portfolioSecuritiesList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PortfolioSecurities> getPortfolioSecuritiesList() {
        return portfolioSecuritiesList;
    }

    public void setPortfolioSecuritiesList(List<PortfolioSecurities> portfolioSecuritiesList) {
        this.portfolioSecuritiesList = portfolioSecuritiesList;
    }
}
