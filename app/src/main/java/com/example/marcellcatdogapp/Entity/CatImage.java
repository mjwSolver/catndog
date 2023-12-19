package com.example.marcellcatdogapp.Entity;

import com.google.gson.annotations.SerializedName;

public class CatImage implements AnimalImage {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

}
