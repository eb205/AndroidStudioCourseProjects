package com.example.restoranker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText billInput;
    private EditText tipInput;
    private EditText totalBillInput;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.billInput = findViewById(R.id.bill);
        this.tipInput = findViewById(R.id.tip);
        this.totalBillInput = findViewById(R.id.totalBill);
        this.mediaPlayer = MediaPlayer.create(this, R.raw.cashsound);

        Intent rankins = getIntent();

        if(rankins != null) {
            //int tip = getTipBasedOnRanking(rankins);
            int tip = getTipBasedOnRanking();
            this.tipInput.setText(Float.toString(tip));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences lastLocationShared = getSharedPreferences(Dispatcher.LAST_LOCATION_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor lastLocationEditor = lastLocationShared.edit();
        lastLocationEditor.putString(Dispatcher.LAST_CLASS_NAME_KEY, FoodRanking.class.getName());
        lastLocationEditor.commit();
    }

    private int getTipBasedOnRanking(Intent rankins) {
        int foodRank = rankins.getIntExtra(FoodRanking.FOOD_STAR_RATE_KEY, 0);
        int foodAmount = rankins.getIntExtra(FoodRanking.AMOUNT_OF_FOOD_KEY, 0) / 10;
        int foodHotValue = rankins.getBooleanExtra(FoodRanking.HOT_FOOD_CHECK_KEY, false) ? 10:0;
        int priceFairValue = rankins.getBooleanExtra(FoodRanking.IS_PRICE_FAIR_KEY, false) ? 10:0;
        int waiterRank = rankins.getIntExtra(ServiceRanking.WAITER_STAR_RANKING_KEY, 0);
        int envPlesentValue = rankins.getBooleanExtra(ServiceRanking.ENV_PLESENT_KEY, false) ? 10:0;
        int toiletCleanValue = rankins.getBooleanExtra(ServiceRanking.IS_TOILET_CLEAN_KEY, false) ? 10:0;

        return foodRank + foodAmount + foodHotValue + priceFairValue + waiterRank + envPlesentValue + toiletCleanValue;
    }

    private int getTipBasedOnRanking() {
        SharedPreferences foodRanking = getSharedPreferences(FoodRanking.FOOD_RANKING_SHARED_FILE_NAME, MODE_PRIVATE);
        SharedPreferences serviceRanking = getSharedPreferences(ServiceRanking.SERVICE_RATING_FILE_NAME, MODE_PRIVATE);

        float foodRank = foodRanking.getFloat(FoodRanking.FOOD_STAR_RATE_KEY, 0);
        int foodAmount = foodRanking.getInt(FoodRanking.AMOUNT_OF_FOOD_KEY, 0) / 10;
        int foodHotValue = foodRanking.getBoolean(FoodRanking.HOT_FOOD_CHECK_KEY, false) ? 10:0;
        int priceFairValue = foodRanking.getBoolean(FoodRanking.IS_PRICE_FAIR_KEY, false) ? 10:0;
        float waiterRank = serviceRanking.getFloat(ServiceRanking.WAITER_STAR_RANKING_KEY, 0);
        int envPlesentValue = serviceRanking.getBoolean(ServiceRanking.ENV_PLESENT_KEY, false) ? 10:0;
        int toiletCleanValue = serviceRanking.getBoolean(ServiceRanking.IS_TOILET_CLEAN_KEY, false) ? 10:0;

        return Math.round(foodRank + foodAmount + foodHotValue + priceFairValue + waiterRank + envPlesentValue + toiletCleanValue);
    }

    public void calcBill(View view) {
        try {
            double billSum = this.billInput.getText().toString().isEmpty() ?
                    0 :
                    Double.parseDouble(this.billInput.getText().toString());
            int tipSum =  this.tipInput.getText().toString().isEmpty() ?
                    0 :
                    Integer.parseInt(this.tipInput.getText().toString());
            double totalSum = billSum * (1 + tipSum * 0.01);
            this.totalBillInput.setText(String.format("%.2f", totalSum));
            this.mediaPlayer.start();
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Invalid value was given", Toast.LENGTH_SHORT).show();
        }

    }
}