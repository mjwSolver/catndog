package com.example.marcellcatdogapp.Entity.Fact;

import com.example.marcellcatdogapp.Entity.AnimalFactAPI;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogFactAPI extends AnimalFactAPI {

    @GET("/v2/facts")
    Call<DogFact> getDogFact();

}
