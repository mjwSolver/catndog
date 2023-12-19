package com.example.marcellcatdogapp.Entity;

import com.google.gson.annotations.SerializedName;

public class DogImage implements AnimalImage {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

}
