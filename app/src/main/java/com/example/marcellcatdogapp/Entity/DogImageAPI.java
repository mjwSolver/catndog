package com.example.marcellcatdogapp.Entity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogImageAPI {
    @GET("/V1/images/search")
    Call<List<DogImage>> getData();
}
