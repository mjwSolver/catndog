package com.example.marcellcatdogapp.Entity;

import com.example.marcellcatdogapp.Entity.CatImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatImageAPI extends AnimalImageAPI {
    @GET("/V1/images/search")
    Call<List<CatImage>> getData();
}
