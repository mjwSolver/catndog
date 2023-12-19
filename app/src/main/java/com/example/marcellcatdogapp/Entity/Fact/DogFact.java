package com.example.marcellcatdogapp.Entity.Fact;

import com.example.marcellcatdogapp.Entity.AnimalFact;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DogFact implements AnimalFact {

    // Kinduff API Serialization
    @SerializedName("facts")
    private ArrayList<String> facts;

    @SerializedName("success")
    private boolean success;

    public ArrayList<String> getText() {
        return this.facts;
    }

    public boolean getStatus() {
        return this.success;
    }

}

// dogapi.dog
//    @SerializedName("body")
//    private String text;
//
//    public String getText() {
//        return this.text;
//    }
