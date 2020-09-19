package com.example.mysqlapp;

import android.provider.BaseColumns;


public final class MyDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MyDatabaseContract() {}
    public static final String DB_NAME = "MyDatabase.db";
    /* Inner class that defines the table contents */
    public static abstract class TableWorkers implements BaseColumns {
        public static final String TABLE_NAME = "workers";
        public static final String COLUMN_NAME_WORKER_ID = "workerid";
        public static final String COLUMN_NAME_FIRST_NAME = "fname";
        public static final String COLUMN_NAME_LAST_NAME = "lname";
    }

    public static abstract class TableProducts implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCT_ID = "productId";
        public static final String COLUMN_PRODUCT_NAME = "productName";

    }
}


