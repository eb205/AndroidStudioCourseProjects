package com.example.mylocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private final static long updateInerval = 5 * 1000;
    private final static float distanceInterval = 10;

    private LocationManager locationManager;
    private WebView webView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this,
                      "Location permissions were not given",
                            Toast.LENGTH_LONG).show();
            return;
        }

        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                                    updateInerval,
                                                    distanceInterval,
                                            this);


        this.textView = findViewById(R.id.textView);
        Location location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double lang = location.getLongitude();
        double lat = location.getLatitude();

        this.textView.setText("Location: " + lang + ", " + lat);

        this.webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lang;
        this.webView.loadUrl(url);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lang = location.getLongitude();
        double lat = location.getLatitude();
        this.textView.setText("Location: " + lang + ", " + lat);

        String url = "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lang;
        this.webView.loadUrl(url);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}