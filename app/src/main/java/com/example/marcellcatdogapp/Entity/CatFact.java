package com.example.marcellcatdogapp.Entity;

import com.google.gson.annotations.SerializedName;

public class CatFact {

    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

}
