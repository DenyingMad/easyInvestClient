package com.cgpanda.easyinvest.Entity.Securities.CommonStock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class CommonStockExtended {
    @SerializedName("securities")
    @Expose
    private List<StaticData> staticDataList;

    @SerializedName("marketdata")
    @Expose
    private List<MarketData> marketDataList;


    private static class StaticData{
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

        @SerializedName("LOTSIZE")
        @Expose
        private int lotSize;

        @SerializedName("BOARDNAME")
        @Expose
        private String boardname;

        @SerializedName("DECIMALS")
        @Expose
        private int decimals;

        @SerializedName("SECNAME")
        @Expose
        private String secname;
    }

    private static class MarketData{
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

        @SerializedName("LASTCHANGE")
        @Expose
        private BigDecimal lastChange;

        @SerializedName("LASTCHANGEPRCNT")
        @Expose
        private BigDecimal lastChangePrcnt;
    }
}
