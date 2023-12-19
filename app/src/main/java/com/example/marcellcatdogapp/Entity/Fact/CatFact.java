package com.example.marcellcatdogapp.Entity.Fact;

import com.example.marcellcatdogapp.Entity.AnimalFact;
import com.google.gson.annotations.SerializedName;

public class CatFact implements AnimalFact {

    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

}
