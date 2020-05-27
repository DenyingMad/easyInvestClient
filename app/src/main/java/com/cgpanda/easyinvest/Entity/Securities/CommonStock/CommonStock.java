package com.cgpanda.easyinvest.Entity.Securities.CommonStock;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class CommonStock {
    @SerializedName("securities")
    @Expose
    private List<StaticData> staticDataList;

    @SerializedName("marketdata")
    @Expose
    private List<MarketData> marketDataList;

    public List<StaticData> getStaticDataList() {
        return staticDataList;
    }

    public void setStaticDataList(List<StaticData> staticDataList) {
        this.staticDataList = staticDataList;
    }

    public List<MarketData> getMarketDataList() {
        return marketDataList;
    }

    public void setMarketDataList(List<MarketData> marketDataList) {
        this.marketDataList = marketDataList;
    }

    public static class StaticData implements Parcelable{
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

        protected StaticData(Parcel in) {
            secid = in.readString();
            boardid = in.readString();
            shortName = in.readString();
            decimals = in.readInt();
        }

        public static final Creator<StaticData> CREATOR = new Creator<StaticData>() {
            @Override
            public StaticData createFromParcel(Parcel in) {
                return new StaticData(in);
            }

            @Override
            public StaticData[] newArray(int size) {
                return new StaticData[size];
            }
        };

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getSecid() {
            return secid;
        }

        public void setSecid(String secid) {
            this.secid = secid;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(secid);
            dest.writeString(boardid);
            dest.writeString(shortName);
            dest.writeInt(decimals);
        }
    }

    public static class MarketData implements Parcelable {
        @SerializedName("SECID")
        @Expose
        private String secid;

        @SerializedName("BOARDID")
        @Expose
        private String boardid;

        @SerializedName("LAST")
        @Expose
        private BigDecimal last;

        @SerializedName("LASTCHANGE")
        @Expose
        private BigDecimal lastChange;

        @SerializedName("LASTCHANGEPRCNT")
        @Expose
        private BigDecimal lastChangePrcnt;

        protected MarketData(Parcel in) {
            secid = in.readString();
            boardid = in.readString();
        }

        public static final Creator<MarketData> CREATOR = new Creator<MarketData>() {
            @Override
            public MarketData createFromParcel(Parcel in) {
                return new MarketData(in);
            }

            @Override
            public MarketData[] newArray(int size) {
                return new MarketData[size];
            }
        };

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

        public BigDecimal getLast() {
            if (last == null) return BigDecimal.valueOf(0);
            return last;
        }

        public void setLast(BigDecimal last) {
            this.last = last;
        }

        public BigDecimal getLastChange() {
            if (lastChange == null) return BigDecimal.valueOf(0.0);
            return lastChange;
        }

        public void setLastChange(BigDecimal lastChange) {
            this.lastChange = lastChange;
        }

        public BigDecimal getLastChangePrcnt() {
            return lastChangePrcnt;
        }

        public void setLastChangePrcnt(BigDecimal lastChangePrcnt) {
            this.lastChangePrcnt = lastChangePrcnt;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(secid);
            dest.writeString(boardid);
        }
    }
}
