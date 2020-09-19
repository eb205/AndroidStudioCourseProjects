package com.example.driver;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private final static String SAVED_LOACTION_FILE_NAME = "SavedLocation";
    private final static String LOCATION_KEY = "location";
    private final static long updateInerval = 5 * 1000;
    private final static float distanceInterval = 10;
    private DriverDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase writeDatabase;


    private LocationManager locationManager;
    private WebView mapView;

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

        this.mapView = findViewById(R.id.webView);

        SharedPreferences savedLocation = getSharedPreferences(SAVED_LOACTION_FILE_NAME, MODE_PRIVATE);
        String lastLocation = savedLocation.getString(LOCATION_KEY, "");

        if(!lastLocation.isEmpty()) {
            this.loadLocationOnMap(lastLocation);
        }

        this.myDatabaseHelper = new DriverDatabaseHelper(this);
        this.writeDatabase = this.myDatabaseHelper.getWritableDatabase();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

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

    public void saveLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permissions were not given", Toast.LENGTH_LONG).show();
            return;
        }

        Location location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double lang = location.getLongitude();
        double lat = location.getLatitude();

        loadLocationOnMap(lat + "," + lang);
        SharedPreferences savedLocation = getSharedPreferences(SAVED_LOACTION_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = savedLocation.edit();
        editor.putString(LOCATION_KEY, lat + "," + lang);
        editor.commit();

        ContentValues values = new ContentValues();
        values.put(MyDatabaseContract.ParkingLocationsTable.LOCATION_LANG_COLUMN_NAME, Double.toString(lang));
        values.put(MyDatabaseContract.ParkingLocationsTable.LOCATION_LAT_COLUMN_NAME, Double.toString(lat));

        writeDatabase.insert(
                MyDatabaseContract.ParkingLocationsTable.TABLE_NAME,
                null,
                values);
    }

    public void loadLocationOnMap(String location) {
        this.mapView = findViewById(R.id.webView);
        mapView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = this.mapView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "https://www.google.com/maps/search/?api=1&query=" + location;
        this.mapView.loadUrl(url);
    }

    public void goToViewOldLocations(View view) {
        Intent intent = new Intent(this, OldParkingLocations.class);
        startActivity(intent);
    }
}