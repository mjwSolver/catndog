package com.example.marcellcatdogapp.Entity;

import com.google.gson.annotations.SerializedName;

public class DogImage {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

}
