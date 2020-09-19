package com.example.stackoverflowsearcher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SearcherDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = DatabaseContract.DB_NAME;
    private static final String TEXT_TYPE = " TEXT";

    private final static String CREATE_PAST_SEARCHES_TABLE =
            "CREATE TABLE " + DatabaseContract.PastSearchesTable.TABLE_NAME + " ( " +
                DatabaseContract.PastSearchesTable.SEARCH_ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseContract.PastSearchesTable.SEARCH_TEXT_COLUMN_NAME + TEXT_TYPE + "," +
                DatabaseContract.PastSearchesTable.SEARCH_LOCATION_COLUMN_NAME + TEXT_TYPE +    " )";

    private final static String DELETE_PAST_SEARCHER_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseContract.PastSearchesTable.TABLE_NAME;

    public SearcherDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PAST_SEARCHES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_PAST_SEARCHER_TABLE);
        onCreate(sqLiteDatabase);
    }
}
