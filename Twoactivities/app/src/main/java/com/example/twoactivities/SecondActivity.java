package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.textView = findViewById(R.id.textView);
        this.editText = findViewById(R.id.textMessageToFirst);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra(MainActivity.MESSAGE_KEY));
    }

    public void goFirst(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.MESSAGE_KEY, this.editText.getText().toString());
        startActivity(intent);
    }
}