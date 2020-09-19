package com.example.shared;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public final static String SHARED_FILE_NAME = "mySharedPreFile";
    public final static String EDIT_TEXT_VALUE_KEY = "editTextValue";


    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.editText = findViewById(R.id.editText);

        SharedPreferences shared = getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE);

        String value = shared.getString(EDIT_TEXT_VALUE_KEY, "");

        if(!value.isEmpty()) {
            this.editText.setText(value);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences shared = getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = shared.edit();
        sharedEditor.putString(EDIT_TEXT_VALUE_KEY, editText.getText().toString());
        sharedEditor.commit();
    }

    public void goA2(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}