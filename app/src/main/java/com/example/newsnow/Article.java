package com.example.newsnow;

public class Article {
    private String title;
    private String sourceName;
    private String urlToImage;
    private String description;
    private String url; // Add a field for the article URL

    // Constructor
    public Article(String title, String sourceName, String urlToImage, String description, String url) {
        this.title = title;
        this.sourceName = sourceName;
        this.urlToImage = urlToImage;
        this.description = description;
        this.url = url; // Initialize the URL
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() { // Add the getter for the URL
        return url;
    }
}
