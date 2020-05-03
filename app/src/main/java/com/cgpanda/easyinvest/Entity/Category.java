package com.cgpanda.easyinvest.Entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    @SerializedName("stories")
    @Expose
    private List<Story> storyList;

    public Category(long id, String categoryName, List<Story> storyList) {
        this.id = id;
        this.categoryName = categoryName;
        this.storyList = storyList;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
