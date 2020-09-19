package com.example.virtualpsychologist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ScrollView scrollViewQ;
    private ScrollView scrollViewA;

    private VideoView video;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.scrollViewQ = findViewById(R.id.scrollViewQ);
        this.scrollViewA = findViewById(R.id.scrollViewA);

        this.video = findViewById(R.id.videoView);
        this.webView = findViewById(R.id.webView);
    }

    public void getAnswer(View view) {

        startActivity(new Intent(this, AnswerActivity.class));
    }

    public void goToCard(View view) {
        startActivity(new Intent(this, CardActivity.class));
    }


}