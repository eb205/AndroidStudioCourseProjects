package com.example.mysqlapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sergey on 22/12/2015.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = MyDatabaseContract.DB_NAME;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_WORKERS =
            "CREATE TABLE " + MyDatabaseContract.TableWorkers.TABLE_NAME  + " (" +
                    MyDatabaseContract.TableWorkers.COLUMN_NAME_WORKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDatabaseContract.TableWorkers.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    MyDatabaseContract.TableWorkers.COLUMN_NAME_LAST_NAME + TEXT_TYPE + " )";

    private static final String SQL_CREATE_PRODUCTS =
            "CREATE TABLE " + MyDatabaseContract.TableProducts.TABLE_NAME  + " (" +
                    MyDatabaseContract.TableProducts.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDatabaseContract.TableProducts.COLUMN_PRODUCT_NAME + TEXT_TYPE + " )";

    private static final String SQL_DELETE_WORKERS =
            "DROP TABLE IF EXISTS " + MyDatabaseContract.TableWorkers.TABLE_NAME;

    private static final String SQL_DELETE_PRODUCTS =
            "DROP TABLE IF EXISTS " + MyDatabaseContract.TableProducts.TABLE_NAME;

    public MyDatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WORKERS);
        db.execSQL(SQL_CREATE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_WORKERS);
        db.execSQL(SQL_DELETE_PRODUCTS);
        onCreate(db);        }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}


