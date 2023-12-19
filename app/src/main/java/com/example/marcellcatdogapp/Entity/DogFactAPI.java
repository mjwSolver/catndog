package com.example.marcellcatdogapp.Entity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogFactAPI extends AnimalFactAPI {

    @GET("/v2/facts")
    Call<DogFact> getDogFact();

}
