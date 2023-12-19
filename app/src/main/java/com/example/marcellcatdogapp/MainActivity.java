package com.example.marcellcatdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.marcellcatdogapp.Entity.AnimalKey;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ImageButton catButton = findViewById(R.id.catImageButton);
            ImageButton dogButton = findViewById(R.id.dogImageButton);

            catButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAnimalGeneratorActivity(String.valueOf(AnimalKey.CAT));
                }
            });

            dogButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    goToAnimalGeneratorActivity(String.valueOf(AnimalKey.DOG));
                }
            });


//            catButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent animalGeneratorIntent = new Intent(MainActivity.this, AnimalGenerator.class);
//                    animalGeneratorIntent.putExtra("ANIMAL", "CAT");
//                    startActivity(animalGeneratorIntent);
//                    finish();
//                }
//            });

//            dogButton.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View v) {
//                    Intent animalGeneratorIntent = new Intent(MainActivity.this, AnimalGenerator.class);
//                    animalGeneratorIntent.putExtra("ANIMAL", "DOG");
//                    startActivity(animalGeneratorIntent);
//                    finish();
//                }
//            });


//        CardView catCardView = findViewById(R.id.catCardView);
//        CardView dogCardView = findViewById(R.id.dogCardView);
//
//        catCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToAnimalGeneratorActivity("CAT");
//            }
//        });
//
//        dogCardView.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                goToAnimalGeneratorActivity("DOG");
//            }
//        });

        }

        private void goToAnimalGeneratorActivity(String withAnimalKey) {
            Intent animalGeneratorIntent = new Intent(this, AnimalGenerator.class);
            animalGeneratorIntent.putExtra(String.valueOf(AnimalKey.ANIMAL), withAnimalKey);
            startActivity(animalGeneratorIntent);
            finish();
        }

}