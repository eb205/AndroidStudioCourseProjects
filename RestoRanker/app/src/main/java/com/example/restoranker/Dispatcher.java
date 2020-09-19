package com.example.restoranker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Dispatcher extends Activity {
    public final static String LAST_LOCATION_FILE_NAME = "lastLocation";
    public final static String LAST_CLASS_NAME_KEY = "lastClass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shared = getSharedPreferences(LAST_LOCATION_FILE_NAME, MODE_PRIVATE);
        String lastPageClassName = shared.getString(LAST_CLASS_NAME_KEY, "");
        Intent intent;

        if(!lastPageClassName.isEmpty()) {
            try {
                intent = new Intent(this, Class.forName(lastPageClassName));
            }
            catch (ClassNotFoundException ex) {
                intent = new Intent(this, FoodRanking.class);
            }
        } else {
            intent = new Intent(this, FoodRanking.class);
        }

        startActivity(intent);
    }
}