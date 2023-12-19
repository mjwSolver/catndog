package com.example.marcellcatdogapp.Entity.Fact;

import com.example.marcellcatdogapp.Entity.AnimalFactAPI;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatFactAPI extends AnimalFactAPI {

    @GET("/facts/random")
    Call<CatFact> getCatFact();

}
