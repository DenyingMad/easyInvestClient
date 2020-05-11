package com.cgpanda.easyinvest.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiKey {

    @SerializedName("apiKey")
    @Expose
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
