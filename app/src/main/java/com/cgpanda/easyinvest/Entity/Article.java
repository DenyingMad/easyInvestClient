package com.cgpanda.easyinvest.Entity;

public class Article {
    private String title;
    private String image;
    private String text;

    public Article(String title, String image, String text) {
        this.title = title;
        this.image = image;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
