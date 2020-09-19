package com.example.stackoverflowsearcher;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements OnSearchFragmentClick, LocationListener {
    public final static String SETTING_PREFRENCE_FILE = "Setting";
    public final static String LAST_SCREEN_PREFRENCE_KEY = "LastScreen";
    public final static String LAST_SEARCH_PREFRENCE_KEY = "LastSearch";

    private final static long updateInerval = 5 * 1000;
    private final static float distanceInterval = 10;
    private final static String DEFAULT_LOCATION = "32.0879994,34.8322654";



    private SearcherDatabaseHelper databaseHelper;
    private SQLiteDatabase writeDatabase;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences preferences =
                getSharedPreferences(SETTING_PREFRENCE_FILE, MODE_PRIVATE);
        Intent intentFromOtherActivity = getIntent();
        String lastScreenClassName = "";

        if(intentFromOtherActivity != null) {
            lastScreenClassName = intentFromOtherActivity.getStringExtra(LAST_SCREEN_PREFRENCE_KEY);
        }

        if(lastScreenClassName == null || lastScreenClassName.isEmpty()) {
            lastScreenClassName =
                    preferences.getString(LAST_SCREEN_PREFRENCE_KEY, SearchBarFragment.class.getName());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if(lastScreenClassName.equals(SearchBarFragment.class.getName())) {
            SearchBarFragment mainPage = SearchBarFragment.newInstance();
            fragmentTransaction.add(R.id.nav_host_fragment, mainPage).commit();
        } else if(lastScreenClassName.equals(SearchResultsFragment.class.getName())){
            String searchText = preferences.getString(LAST_SEARCH_PREFRENCE_KEY, "");
            SearchResultsFragment searchResultsFragments = SearchResultsFragment.newInstace(searchText);
            fragmentTransaction.add(R.id.nav_host_fragment, searchResultsFragments).commit();
        } else if(lastScreenClassName.equals(ProgramerInfo.class.getName())) {
            Intent intent = new Intent(this, ProgramerInfo.class);
            startActivity(intent);
        } else if(lastScreenClassName.equals(SearchHistoryFragment.class.getName())) {
            SearchHistoryFragment historyFragment = SearchHistoryFragment.newInstance();
            fragmentTransaction.add(R.id.nav_host_fragment, historyFragment).commit();
        } else {
            throw new RuntimeException("Cannot find class " + lastScreenClassName);
        }

        this.databaseHelper = new SearcherDatabaseHelper(this);
        this.writeDatabase = this.databaseHelper.getWritableDatabase();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);

        if (id == R.id.action_home) {
            if(currFragment != null && currFragment instanceof SearchBarFragment) {
                return true;
            }

            if(currFragment != null) {
                fragmentTransaction = fragmentTransaction.remove(currFragment);
            }

            fragmentTransaction.add(R.id.nav_host_fragment, SearchBarFragment.newInstance())
                    .addToBackStack(null)
                    .commit();

            return true;
        } else if(id == R.id.action_history){
            if(currFragment != null && currFragment instanceof SearchHistoryFragment) {
                return true;
            }

            if(currFragment != null) {
                fragmentTransaction = fragmentTransaction.remove(currFragment);
            }

            fragmentTransaction.add(R.id.nav_host_fragment, SearchHistoryFragment.newInstance())
                    .addToBackStack(null)
                    .commit();

            return true;
        } else if(id == R.id.action_programer_profile) {
            Intent intent = new Intent(this, ProgramerInfo.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchButtonClick(String searchText) {
        saveSearchOnDb(searchText);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchBarFragment searchFragment =
                (SearchBarFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);

        if(searchFragment != null) {
            fragmentTransaction = fragmentTransaction.remove(searchFragment);
        }

        SearchResultsFragment searchResultsFragments = SearchResultsFragment.newInstace(searchText);


        fragmentTransaction.add(R.id.nav_host_fragment, searchResultsFragments).addToBackStack(null).commit();
    }

    public void saveSearchOnDb(String searchText) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.PastSearchesTable.SEARCH_TEXT_COLUMN_NAME, searchText);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permissions were not given", Toast.LENGTH_LONG).show();
            values.put(DatabaseContract.PastSearchesTable.SEARCH_LOCATION_COLUMN_NAME,
                    DEFAULT_LOCATION);
        } else {
            Location location =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            values.put(DatabaseContract.PastSearchesTable.SEARCH_LOCATION_COLUMN_NAME,
                    location.getLatitude() + ", " + location.getLongitude());
        }

        this.writeDatabase.insert(DatabaseContract.PastSearchesTable.TABLE_NAME,
                null,
                values);
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
}