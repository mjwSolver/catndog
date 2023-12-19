package com.example.marcellcatdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.example.marcellcatdogapp.databinding.ActivityGeneratorBinding;
import com.example.marcellcatdogapp.databinding.ActivityTriviaBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Trivia extends AppCompatActivity {

    String currentAnimal;
    String answerKey;
    ActivityTriviaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTriviaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadTrivia();

    }

    private void loadTrivia() {
        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("cat");

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
            binding.answerOneTextView.setText(response1);
            binding.answerTwoTextView.setText(response2);
            binding.answerThreeTextView.setText(response3);
            binding.answerFourTextView.setText(response4);

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