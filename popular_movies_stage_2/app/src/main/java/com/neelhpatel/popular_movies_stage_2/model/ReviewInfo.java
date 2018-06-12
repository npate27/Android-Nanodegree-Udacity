package com.neelhpatel.popular_movies_stage_2.model;

public class ReviewInfo {
    final String author;
    final String textContent;
    final String url;

    public ReviewInfo(String author, String textContent, String url) {
        this.author = author;
        this.textContent = textContent;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getUrl() {
        return url;
    }
}
