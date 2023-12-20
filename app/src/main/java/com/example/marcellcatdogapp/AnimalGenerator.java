package com.example.marcellcatdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcellcatdogapp.Entity.AnimalKey;
import com.example.marcellcatdogapp.Entity.Fact.CatFact;
import com.example.marcellcatdogapp.Entity.Fact.CatFactAPI;
import com.example.marcellcatdogapp.Entity.Image.CatImage;
import com.example.marcellcatdogapp.Entity.Image.CatImageAPI;
import com.example.marcellcatdogapp.Entity.Fact.DogFact;
import com.example.marcellcatdogapp.Entity.Fact.DogFactAPI;
import com.example.marcellcatdogapp.Entity.Image.DogImage;
import com.example.marcellcatdogapp.Entity.Image.DogImageAPI;
import com.example.marcellcatdogapp.databinding.ActivityGeneratorBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalGenerator extends AppCompatActivity {

    String currentAnimal;
    ActivityGeneratorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGeneratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        loadListeners();

    }

    private void loadListeners(){

        binding.nextFactButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              createNextFact();
          }
      });

        binding.goToTriviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent triviaIntent = new Intent(AnimalGenerator.this, Trivia.class);
                triviaIntent.putExtra(AnimalKey.ANIMAL.name(), currentAnimal);
                startActivity(triviaIntent);
                finish();
            }
        });

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

    private void createNextFact(){

        if (currentAnimal.equals(AnimalKey.CAT.name())) {
            generateCatFact();
        } else if (currentAnimal.equals(AnimalKey.DOG.name())) {
            generateDogFact();
        }

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
                System.out.println("Failed to Acquire Cat Image");
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
//                .baseUrl("https://dogapi.dog")
                .baseUrl("https://dog-api.kinduff.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogFactAPI dogFactApi = retrofit.create(DogFactAPI.class);
        Call<DogFact> factCall = dogFactApi.getDogFact();
        factCall.enqueue(new Callback<DogFact>() {
            @Override
            public void onResponse(Call<DogFact> call, Response<DogFact> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> text = response.body().getText();
                    animalFactText.setText(text.get(0));
                }
            }

            @Override
            public void onFailure(Call<DogFact> call, Throwable t) {
                System.out.println("failed to get dog fact");
            }

        });
    }

}