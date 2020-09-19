package com.example.threebuttons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public final static String PASSAGE_KEY = "PASSAGE_TEXT";

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btn1 = findViewById(R.id.button1);
        this.btn2 = findViewById(R.id.button2);
        this.btn3 = findViewById(R.id.button3);
    }

    public void goToSecond(View view) {
        Intent goToText = new Intent(this, TextActivity.class);
        Log.d("View ID", "" + view.getId());
        switch (view.getId())
        {
            case R.id.button1:{
                goToText.putExtra(PASSAGE_KEY, getString(R.string.passage_one));
                break;
            }
            case R.id.button2: {
                goToText.putExtra(PASSAGE_KEY, getString(R.string.passage_two));
                break;
            }
            case R.id.button3: {
                goToText.putExtra(PASSAGE_KEY, getString(R.string.passage_three));
                break;
            }
        }

        startActivity(goToText);
    }
}