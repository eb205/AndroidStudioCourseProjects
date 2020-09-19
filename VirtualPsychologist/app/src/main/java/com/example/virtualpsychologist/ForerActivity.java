package com.example.virtualpsychologist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForerActivity extends AppCompatActivity {
    private VideoView video;
    private WebView webView;
    private FloatingActionButton cardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forer);
        this.video = findViewById(R.id.videoView);
        this.webView = findViewById(R.id.webView);
        this.cardButton = findViewById(R.id.card);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.forer);
        this.video.setVideoURI(videoUri);
        this.video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();

        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("https://he.wikipedia.org/wiki/%D7%90%D7%A4%D7%A7%D7%98_%D7%A4%D7%95%D7%A8%D7%A8");
    }

    public void goToCard(View view) {
        startActivity(new Intent(this, CardActivity.class));
    }
}