package com.example.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText websiteText;
    private EditText locationText;
    private EditText shareText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.websiteText = findViewById(R.id.websiteText);
        this.locationText = findViewById(R.id.locationText);
        this.shareText = findViewById(R.id.shareText);
    }

    public void shareText(View view) {
        String txt = shareText.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share_chooser_title)
                .setText(txt)
                .startChooser();
    }

    public void openLocation(View view) {
        String loc = locationText.getText().toString();

        if(!loc.isEmpty()) {
            Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
            Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d(getPackageName(), "Cant handle this");
            }
        }
    }

    public void openWebsite(View view) {
        String websiteUrl = websiteText.getText().toString();

        if(!websiteUrl.isEmpty()) {
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
            if(websiteIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(websiteIntent);
            }
        } else {
            Log.d(getPackageName(), "Cant handle this");
        }
    }

    public void takePic(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}