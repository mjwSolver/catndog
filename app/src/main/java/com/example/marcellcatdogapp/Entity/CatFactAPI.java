package com.example.marcellcatdogapp.Entity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatFactAPI extends AnimalFactAPI {

    @GET("/facts/random")
    Call<CatFact> getCatFact();

}
