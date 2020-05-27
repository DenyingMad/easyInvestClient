package com.cgpanda.easyinvest.Entity.PortfolioSecurities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class PortfolioSecuritiesStaticData {
    @SerializedName("SECID")
    @Expose
    private String secid;
    @SerializedName("BOARDID")
    @Expose
    private String boardid;
    @SerializedName("SHORTNAME")
    @Expose
    private String shortName;
    @SerializedName("PREVPRICE")
    @Expose
    private BigDecimal prevPrice;
    @SerializedName("DECIMALS")
    @Expose
    private int decimals;


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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public BigDecimal getPrevPrice() {
        return prevPrice;
    }

    public void setPrevPrice(BigDecimal prevPrice) {
        this.prevPrice = prevPrice;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }
}
//SECID": "POLY", "BOARDID": "TQBR", "SHORTNAME": "Polymetal", "PREVPRICE": 1518, "LOTSIZE": 1, "FACEVALUE": 0, "STATUS": "A"," +
//        " "BOARDNAME": "Т+: Акции и ДР - безадрес.", "DECIMALS": 1, "SECNAME": "Polymetal International plc", "REMARKS": null, "MARKETCODE": "FNDT", "INSTRID": "EQIN"," +
//        " "SECTORID": "EQ-N", "MINSTEP": 0.1, "PREVWAPRICE": 1504.6, "FACEUNIT": "GBP", "PREVDATE": "2020-05-19", " +
//        ""ISSUESIZE": 471791037, "ISIN": "JE00B6T5S470", "LATNAME": "Polymetal International plc", "REGNUMBER": null, "PREVLEGALCLOSEPRICE": 1518, "PREVADMITTEDQUOTE": 1518,