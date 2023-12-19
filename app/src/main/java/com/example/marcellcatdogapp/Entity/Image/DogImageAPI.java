package com.example.marcellcatdogapp.Entity.Image;

import com.example.marcellcatdogapp.Entity.AnimalImageAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogImageAPI extends AnimalImageAPI {
    @GET("/V1/images/search")
    Call<List<DogImage>> getData();
}
