package com.example.threebuttons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextActivity extends AppCompatActivity {
    private TextView textPassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Intent intent = getIntent();
        this.textPassage = findViewById(R.id.textPassage);
        this.textPassage.setText(intent.getStringExtra(MainActivity.PASSAGE_KEY));
    }
}