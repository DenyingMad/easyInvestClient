package com.cgpanda.easyinvest.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("episode_text")
    @Expose
    private String episode_text;

    @SerializedName("episode_image")
    @Expose
    private String episode_image;

    public Episode(long episode_id, String episode_text, String episode_image) {
        this.id = episode_id;
        this.episode_text = episode_text;
        this.episode_image = episode_image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEpisode_text() {
        return episode_text;
    }

    public void setEpisode_text(String episode_text) {
        this.episode_text = episode_text;
    }

    public String getEpisode_image() {
        return episode_image;
    }

    public void setEpisode_image(String episode_image) {
        this.episode_image = episode_image;
    }
}
