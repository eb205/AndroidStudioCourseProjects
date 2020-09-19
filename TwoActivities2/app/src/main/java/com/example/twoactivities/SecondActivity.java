package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";

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
    }

    public void returnReply(View view) {
        Intent replayIntent = new Intent();
        replayIntent.putExtra(EXTRA_REPLY, this.replayMessage.getText().toString());
        setResult(RESULT_OK, replayIntent);
        finish();
    }
}