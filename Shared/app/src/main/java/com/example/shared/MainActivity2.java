package com.example.shared;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.text = findViewById(R.id.textView);
        SharedPreferences shared = getSharedPreferences(MainActivity.SHARED_FILE_NAME, MODE_PRIVATE);
        String str = shared.getString(MainActivity.EDIT_TEXT_VALUE_KEY, "");

        if(!str.isEmpty()) {
            text.setText(str);
        }
    }
}