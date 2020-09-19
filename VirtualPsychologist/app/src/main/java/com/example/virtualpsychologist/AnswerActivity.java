package com.example.virtualpsychologist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
    }

    public void getForer(View view) {
        startActivity(new Intent(this, ForerActivity.class));
    }

    public void goToCard(View view) {
        startActivity(new Intent(this, CardActivity.class));
    }
}