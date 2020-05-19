package com.cgpanda.easyinvest.Entity.Securities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
}
