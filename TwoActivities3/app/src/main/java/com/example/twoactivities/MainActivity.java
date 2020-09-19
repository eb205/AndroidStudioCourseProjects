package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String VISIBLE_KEY = "reply_visible";
    private static final String REPLAY_TEXT_KEY = "reply_text";

    private EditText textMessage;
    private TextView replayHeader;
    private TextView replayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textMessage = findViewById(R.id.editText_main);
        this.replayHeader = findViewById(R.id.text_header_reply);
        this.replayText = findViewById(R.id.text_message_reply);

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        if(savedInstanceState != null &&
            savedInstanceState.getBoolean(VISIBLE_KEY, false)) {
            this.replayText.setText(savedInstanceState.getString(REPLAY_TEXT_KEY));
            this.replayText.setVisibility(View.VISIBLE);
            this.replayHeader.setVisibility(View.VISIBLE);
        }

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

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, this.textMessage.getText().toString());
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TEXT_REQUEST) {
            if(resultCode == RESULT_OK) {
                String replay = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                this.replayText.setText(replay);
                this.replayText.setVisibility(View.VISIBLE);
                this.replayHeader.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(VISIBLE_KEY, this.replayHeader.getVisibility() == View.VISIBLE);
        outState.putString(REPLAY_TEXT_KEY, this.replayText.getText().toString());
    }
}