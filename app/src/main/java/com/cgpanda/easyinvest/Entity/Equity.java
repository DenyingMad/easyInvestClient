package com.cgpanda.easyinvest.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Equity {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("short_name")
    @Expose
    private String shortName;

    public Equity(long id, String fullName, String shortName) {
        this.id = id;
        this.fullName = fullName;
        this.shortName = shortName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
