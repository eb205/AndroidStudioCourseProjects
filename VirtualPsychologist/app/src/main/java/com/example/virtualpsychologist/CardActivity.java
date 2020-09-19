package com.example.virtualpsychologist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CardActivity extends AppCompatActivity {

    private TextView fullName;
    private TextView phoneNumber;
    private TextView location;
    private TextView websiteLink;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        this.fullName = findViewById(R.id.fullName);
        this.phoneNumber = findViewById(R.id.phoneNumber);
        this.location =  findViewById(R.id.location);
        this.websiteLink = findViewById(R.id.websiteLink);
    }

    public void clickPhone(View view) {
        Uri phone = Uri.parse("tel:0547050000");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(phone);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(callIntent);
        }
    }

    public void clickLocation(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:32.0738243512373,34.81033675419836")));
    }

    public void clickWebpage(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com")));
    }
}