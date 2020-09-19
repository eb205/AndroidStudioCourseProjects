package com.example.lightsapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.linearLayout = findViewById(R.id.linearContainer);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void changeColor(View view) {
        linearLayout.setBackground(view.getBackground());

//        Context context = this;
//        String text =  "Button clicked";
//        int duration = Toast.LENGTH_LONG;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.setGravity(Gravity.TOP, 0, 0);
//        toast.show();

        Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show();
    }
}