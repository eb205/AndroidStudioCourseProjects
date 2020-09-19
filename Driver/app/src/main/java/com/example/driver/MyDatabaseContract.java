package com.example.driver;

import android.provider.BaseColumns;

public final class MyDatabaseContract {
    public MyDatabaseContract() {}
    public static final String DB_NAME = "ParkingLocaitons.db";

    public static abstract class ParkingLocationsTable implements BaseColumns {
        public static String TABLE_NAME = "parkingLocationsCoords";
        public static String LOCATION_ID_COLUMN_NAME = "locationId";
        public static String LOCATION_LAT_COLUMN_NAME = "locationLat";
        public static String LOCATION_LANG_COLUMN_NAME = "locationLang";
    }
}
