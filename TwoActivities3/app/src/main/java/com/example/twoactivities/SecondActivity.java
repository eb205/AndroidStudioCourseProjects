package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";

    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    private TextView textMessageFromActivity;
    private EditText replayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.textMessageFromActivity = findViewById(R.id.text_message);
        this.replayMessage = findViewById(R.id.editText_second);
        Intent intent = getIntent();
        this.textMessageFromActivity.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public void returnReply(View view) {
        Intent replayIntent = new Intent();
        replayIntent.putExtra(EXTRA_REPLY, this.replayMessage.getText().toString());
        setResult(RESULT_OK, replayIntent);
        Log.d(LOG_TAG, "End SecondActivity");
        finish();
    }
}