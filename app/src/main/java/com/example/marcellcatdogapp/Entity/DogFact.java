package com.example.marcellcatdogapp.Entity;

import com.google.gson.annotations.SerializedName;

public class DogFact {

    @SerializedName("text")
    private String text;
    public String getText() {
        return text;
    }

}
