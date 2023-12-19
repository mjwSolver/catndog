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
                    goToAnimalGeneratorActivity(AnimalKey.CAT.name());
                }
            });

            dogButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    goToAnimalGeneratorActivity(AnimalKey.DOG.name());
                }
            });

        }

        private void goToAnimalGeneratorActivity(String withAnimalKey) {
            Intent animalGeneratorIntent = new Intent(this, AnimalGenerator.class);
            animalGeneratorIntent.putExtra(AnimalKey.ANIMAL.name(), withAnimalKey);
            startActivity(animalGeneratorIntent);
            finish();
        }

}