package com.cgpanda.easyinvest.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Story {

    public Story(long story_id, String title, String imageUrl) {
        this.story_id = story_id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    @SerializedName("story_id")
    @Expose
    private long story_id;


    @SerializedName("story_title")
    @Expose
    private String title;

    //TODO заменить url ссылку на изображение на само изображение

    @SerializedName("story_image")
    @Expose
    private String imageUrl;

    @SerializedName("episodes")
    @Expose
    private List<Episode> episodeList;

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public long getStory_id() {
        return story_id;
    }

    public void setStory_id(long story_id) {
        this.story_id = story_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
