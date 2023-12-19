package com.example.marcellcatdogapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marcellcatdogapp.Entity.AnimalKey;
import com.example.marcellcatdogapp.Entity.Image.CatImage;
import com.example.marcellcatdogapp.Entity.Image.CatImageAPI;
import com.example.marcellcatdogapp.Entity.Image.DogImage;
import com.example.marcellcatdogapp.Entity.Image.DogImageAPI;
import com.example.marcellcatdogapp.databinding.ActivityTriviaBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Trivia extends AppCompatActivity {

    String currentAnimal;
    String answerKey;
    ActivityTriviaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTriviaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        currentAnimal = extras.getString(AnimalKey.ANIMAL.name());

        loadNewTrivia();
        loadTriviaImage();
        loadListeners();

    }

    private void loadListeners(){

        binding.answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseToClick(binding.answer1Button);
            }
        });
        binding.answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseToClick(binding.answer2Button);
            }
        });
        binding.answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseToClick(binding.answer3Button);
            }
        });
        binding.answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseToClick(binding.answer4Button);
            }
        });
        binding.loadNewTriviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNewTrivia();
                loadTriviaImage();
            }
        });

        binding.goBackToTriviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent animalGeneratorIntent = new Intent(Trivia.this, AnimalGenerator.class);
                animalGeneratorIntent.putExtra(AnimalKey.ANIMAL.name(), currentAnimal);
                startActivity(animalGeneratorIntent);
                finish();
            }
        });

        binding.goBackToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(Trivia.this, MainActivity.class);
//                mainMenuIntent.putExtra(AnimalKey.ANIMAL.name(), currentAnimal);
                startActivity(mainMenuIntent);
                finish();
            }
        });


    }

    private void responseToClick(Button buttonAnswer){
        // If it's correct
        if(buttonAnswer.getText().equals(answerKey)) {

            buttonAnswer.setBackgroundColor(Color.GREEN);

            Toast.makeText(
                    getApplication().getApplicationContext(),
                    "Congratulations, you got it right!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            buttonAnswer.setBackgroundColor(Color.RED);
        }
    }

    private void loadTriviaImage() {
        if(currentAnimal.equals(AnimalKey.DOG.name())){
            generateDogImage();
            Log.i("TRIVIA", "Successful Dog Image Generation");
        } else if (currentAnimal.equals(AnimalKey.CAT.name())){
            generateCatImage();
            Log.i("TRIVIA", "Successful Cat Image Generation");
        } else {
            // Default is to create DogImage
            generateDogImage();
            Log.i("TRIVIA", "Successful Dog Image Generation");
        }
    }

    private void generateDogImage(){

        ImageView triviaImage = binding.triviaImageView;

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
                    Picasso.get().load(imageUrl).into(triviaImage);
                }
            }

            @Override
            public void onFailure(Call<List<DogImage>> call, Throwable t) {
                System.out.println("failed");
            }
        });

    }

    private void generateCatImage(){

        ImageView triviaImage = binding.triviaImageView;

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
                    Picasso.get().load(imageUrl).into(triviaImage);
                    // Will be a Cat
                }
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                System.out.println("Failed to Acquire Cat Image");
            }
        });
    }

    private void loadNewTrivia() {
        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray(currentAnimal.toLowerCase());

            Random rand = new Random();

            int theNumber = rand.nextInt(m_jArry.length());
            JSONObject jo_inside = m_jArry.getJSONObject(theNumber);
            String question = jo_inside.getString("question");
            String response1 = jo_inside.getString("response1");
            String response2 = jo_inside.getString("response2");
            String response3 = jo_inside.getString("response3");
            String response4 = jo_inside.getString("response4");
            answerKey = jo_inside.getString("answerkey");

            binding.questionTextView.setText(question);
            binding.answer1Button.setText(response1);
            binding.answer2Button.setText(response2);
            binding.answer3Button.setText(response3);
            binding.answer4Button.setText(response4);

            String buttonColor = "#943A5E9B";
            binding.answer1Button.setBackgroundColor(Color.parseColor(buttonColor));
            binding.answer2Button.setBackgroundColor(Color.parseColor(buttonColor));
            binding.answer3Button.setBackgroundColor(Color.parseColor(buttonColor));
            binding.answer4Button.setBackgroundColor(Color.parseColor(buttonColor));

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("catdogtrivia.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}