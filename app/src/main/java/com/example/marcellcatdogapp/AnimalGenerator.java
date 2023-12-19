package com.example.marcellcatdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcellcatdogapp.Entity.AnimalImage;
import com.example.marcellcatdogapp.Entity.AnimalImageAPI;
import com.example.marcellcatdogapp.Entity.AnimalKey;
import com.example.marcellcatdogapp.Entity.CatFact;
import com.example.marcellcatdogapp.Entity.CatFactAPI;
import com.example.marcellcatdogapp.Entity.CatImage;
import com.example.marcellcatdogapp.Entity.CatImageAPI;
import com.example.marcellcatdogapp.Entity.DogFact;
import com.example.marcellcatdogapp.Entity.DogFactAPI;
import com.example.marcellcatdogapp.Entity.DogImage;
import com.example.marcellcatdogapp.Entity.DogImageAPI;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalGenerator extends AppCompatActivity {

    String currentAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        Bundle extras = getIntent().getExtras();
        currentAnimal = extras.getString(AnimalKey.ANIMAL.name());

//        ImageView animalImage = findViewById(R.id.animalImageView);
//        TextView animalFactText = findViewById(R.id.animalFactsTextView);

        assert currentAnimal != null;
        if (currentAnimal.equals(AnimalKey.CAT.name())) {
            generateCatImage();
            generateCatFact();
        } else if (currentAnimal.equals(AnimalKey.DOG.name())) {
            generateDogImage();
            generateDogFact();
        } else {
            // Assume default is to generate "Dog"
            generateDogImage();
            generateDogFact();
        }

    }

    private void generateNewFactAndImage(){

        if(currentAnimal == null){
            Toast.makeText(
                    AnimalGenerator.this,
                    "Animal Undefined",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if(currentAnimal.equals(AnimalKey.CAT.name())){

        }

        if(currentAnimal.equals(AnimalKey.DOG.name())){

        }

    }

    private boolean runCurrentAnimalChecker(){

        if(currentAnimal == null){
            Toast.makeText(
                    AnimalGenerator.this,
                    "Animal Undefined",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

        return true;

    }

    private void generateAnimalImage(){

        // This is not working

        if(!runCurrentAnimalChecker()){
            return;
        }

        String theBaseURL = "";
        Class<AnimalImageAPI> futureAnimalImageAPI;

        if(currentAnimal.equals(AnimalKey.CAT.name())){
            theBaseURL = "https://api.thecatapi.com";

        } else if(currentAnimal.equals(AnimalKey.DOG.name())){
            theBaseURL = "https://api.thedogapi.com/";
        }

        ImageView animalImage = findViewById(R.id.animalImageView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(theBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        Half done - you realized you'll need to reformat the entire interface and class structure.

//        AnimalImageAPI animalImageAPI = retrofit.create()


    }

    private void generateCatImage() {

        ImageView animalImage = findViewById(R.id.animalImageView);

        // Generate Cat Image
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatImageAPI catImageApi = retrofit.create(CatImageAPI.class);
        Call<List<CatImage>> call = catImageApi.getData();
        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.isSuccessful()) {
                    List<CatImage> data = response.body();
                    String imageUrl = data.get(0).getUrl();
                    Picasso.get().load(imageUrl).into(animalImage);
                    // Will be a Cat
                }
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                System.out.println("failed");
            }
        });


    }

    private void generateCatFact() {

        TextView animalFactText = findViewById(R.id.animalFactsTextView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cat-fact.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatFactAPI catFactApi = retrofit.create(CatFactAPI.class);
        Call<CatFact> factCall = catFactApi.getCatFact();
        factCall.enqueue(new Callback<CatFact>() {
            @Override
            public void onResponse(Call<CatFact> call, Response<CatFact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String text = response.body().getText();
                    if (Pattern.matches(".*\\p{InCyrillic}.*", text)) {
                        animalFactText.setText("Bro this is Russian");
                    } else {
                        animalFactText.setText(text);
                    }
                }
            }

            @Override
            public void onFailure(Call<CatFact> call, Throwable t) {
                System.out.println("failed");
            }

        });

    }

    private void generateDogImage() {

        ImageView animalImage = findViewById(R.id.animalImageView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogImageAPI dogImageApi = retrofit.create(DogImageAPI.class);
        Call<List<DogImage>> call = dogImageApi.getData();

        call.enqueue(new Callback<List<DogImage>>() {
            @Override
            public void onResponse(Call<List<DogImage>> call, Response<List<DogImage>> response) {
                if (response.isSuccessful()) {
                    List<DogImage> data = response.body();
                    String imageUrl = data.get(0).getUrl();
                    Picasso.get().load(imageUrl).into(animalImage);
                }
            }

            @Override
            public void onFailure(Call<List<DogImage>> call, Throwable t) {
                System.out.println("failed");
            }
        });

    }

    private void generateDogFact(){

        TextView animalFactText = findViewById(R.id.animalFactsTextView);

//        Be careful, we're copying from cat...

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cat-fact.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogFactAPI dogFactApi = retrofit.create(DogFactAPI.class);
        Call<DogFact> factCall = dogFactApi.getDogFact();
        factCall.enqueue(new Callback<DogFact>() {
            @Override
            public void onResponse(Call<DogFact> call, Response<DogFact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String text = response.body().getText();
                    if (Pattern.matches(".*\\p{InCyrillic}.*", text)) {
                        animalFactText.setText("Bro this is Russian");
                    } else {
                        animalFactText.setText(text);
                    }
                }
            }

            @Override
            public void onFailure(Call<DogFact> call, Throwable t) {
                System.out.println("failed to get dog fact");
            }

        });
    }

}