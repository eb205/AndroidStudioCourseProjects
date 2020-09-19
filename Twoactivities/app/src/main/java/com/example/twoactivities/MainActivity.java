package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public final static String MESSAGE_KEY = "main activity message";
    public boolean isTrue;
    private EditText textMessage;
    private TextView textView;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textMessage = findViewById(R.id.editTextMessage);
        this.textView = findViewById(R.id.messegeFromSecond);
        this.checkBox = findViewById(R.id.checkBox);
        this.isTrue = this.checkBox.isChecked();
//        this.checkBox.setOnCheckedChangeListener(new MyListener(this));

        this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Toast.makeText(MainActivity.this, "Checkbox is checked", Toast.LENGTH_LONG).show();
                    MainActivity.this.isTrue = true;
                    textMessage.setText("Checked");
                } else {
                    Toast.makeText(MainActivity.this, "Checkbox is unchecked", Toast.LENGTH_LONG).show();
                    MainActivity.this.isTrue = false;
                    textMessage.setText("Unchecked");
                }
            }
        });

        Intent intent = getIntent();

        if(intent != null) {
            textView.setText(intent.getStringExtra(MESSAGE_KEY));
        }
    }

    public void goSecond(View view) {
        String message = textMessage.getText().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(MESSAGE_KEY, message);

        startActivity(intent);
    }
}