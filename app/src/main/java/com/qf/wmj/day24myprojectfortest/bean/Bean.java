package com.qf.wmj.day24myprojectfortest.bean;

/**
 * Created by JB on 2016/10/15.
 */
public class Bean {
    private String id;
    private String title;
    private String description;
    private String cover_url;
    public Bean() {
        super();
    }
    public Bean(String id, String title, String description, String cover_url) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover_url = cover_url;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCover_url() {
        return cover_url;
    }
    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }
    @Override
    public String toString() {
        return "Bean [id=" + id + ", title=" + title + ", description="
                + description + ", cover_url=" + cover_url + "]";
    }
}
