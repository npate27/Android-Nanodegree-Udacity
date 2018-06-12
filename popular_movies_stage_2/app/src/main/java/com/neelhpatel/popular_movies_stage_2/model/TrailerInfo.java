package com.neelhpatel.popular_movies_stage_2.model;

public class TrailerInfo {
    final String key;
    final String name;

    public TrailerInfo(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
