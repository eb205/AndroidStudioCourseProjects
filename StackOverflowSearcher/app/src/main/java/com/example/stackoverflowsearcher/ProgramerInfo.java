package com.example.stackoverflowsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Random;

public class ProgramerInfo extends AppCompatActivity {
    private VideoView video;
    private MediaPlayer[] screams = new MediaPlayer[3];
    private Random rnd;
    private int clickTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programer_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        screams[0] = MediaPlayer.create(this, R.raw.roblox_ouch);
        screams[1] = MediaPlayer.create(this, R.raw.minecraft_ouch);
        screams[2] =  MediaPlayer.create(this, R.raw.wilham_ouch);
        this.rnd = new Random();
        this.video = findViewById(R.id.videoView);

    }

    public void onPicClick(View view) {
        clickTimes++;

        if(clickTimes == 3) {
            this.video.setVisibility(View.VISIBLE);
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.roll);
            this.video.setVideoURI(videoUri);
            this.video.setMediaController(new MediaController(this));
            video.requestFocus();
            video.start();
            Toast.makeText(this, R.string.warned_you, Toast.LENGTH_LONG).show();
        }

        screams[rnd.nextInt(3)].start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_copy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home2) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.LAST_SCREEN_PREFRENCE_KEY, SearchBarFragment.class.getName());
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences =
                getSharedPreferences(MainActivity.SETTING_PREFRENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainActivity.LAST_SCREEN_PREFRENCE_KEY, this.getClass().getName());
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences preferences =
                getSharedPreferences(MainActivity.SETTING_PREFRENCE_FILE, Context.MODE_PRIVATE);

        String lastClassName = preferences.getString(MainActivity.LAST_SCREEN_PREFRENCE_KEY, "");

        if(lastClassName.equals(getClass().getName())) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.LAST_SCREEN_PREFRENCE_KEY, SearchBarFragment.class.getName());
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }
}