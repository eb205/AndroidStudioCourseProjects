package com.example.driver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DriverDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = MyDatabaseContract.DB_NAME;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_PARKING_LOCATIONS_TABLE =
            "CREATE TABLE " + MyDatabaseContract.ParkingLocationsTable.TABLE_NAME + "( " +
                    MyDatabaseContract.ParkingLocationsTable.LOCATION_ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDatabaseContract.ParkingLocationsTable.LOCATION_LAT_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    MyDatabaseContract.ParkingLocationsTable.LOCATION_LANG_COLUMN_NAME + TEXT_TYPE + " )";

    private static final String DELETE_PARKING_LOCATION_TABLE =
            "DROP TABLE IF EXISTS " + MyDatabaseContract.ParkingLocationsTable.TABLE_NAME;

    public DriverDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PARKING_LOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_PARKING_LOCATION_TABLE);
        onCreate(sqLiteDatabase);
    }
}
