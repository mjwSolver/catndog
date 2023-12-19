package com.example.marcellcatdogapp.Entity;

import com.example.marcellcatdogapp.Entity.Image.CatImage;
import com.example.marcellcatdogapp.Entity.Image.DogImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimalImageAPI {

    @GET("/V1/images/search")
    Call<List<CatImage>> getCatImageData();

    @GET("/V1/images/search")
    Call<List<DogImage>> getDogImageData();

}
