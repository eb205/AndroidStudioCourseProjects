package com.example.driver;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OldParkingLocations extends AppCompatActivity {
    private TextView parkingLocationsView;
    private DriverDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase readDatebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_parking_locations);
        parkingLocationsView = findViewById(R.id.parkingLocations);

        this.myDatabaseHelper = new DriverDatabaseHelper(this);
        this.readDatebase = this.myDatabaseHelper.getReadableDatabase();

        String[] projection = {
            MyDatabaseContract.ParkingLocationsTable.LOCATION_ID_COLUMN_NAME,
            MyDatabaseContract.ParkingLocationsTable.LOCATION_LAT_COLUMN_NAME,
            MyDatabaseContract.ParkingLocationsTable.LOCATION_LANG_COLUMN_NAME
        };

        Cursor resultCursor = this.readDatebase.query(
                MyDatabaseContract.ParkingLocationsTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        String locations = "";
        resultCursor.moveToFirst();

        for(int index = 0; index < resultCursor.getCount(); index++) {
            String currentParkingLocation = "Id: " + resultCursor.getInt(0) + " Lat: " + resultCursor.getString(1) + " Lang: " + resultCursor.getString(2);
            locations += currentParkingLocation + "\n";
            resultCursor.moveToNext();
        }

        this.parkingLocationsView.setText(locations);
    }


}