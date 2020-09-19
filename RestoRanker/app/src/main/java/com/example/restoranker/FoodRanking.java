package com.example.restoranker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class FoodRanking extends AppCompatActivity {
    public final static String FOOD_RANKING_SHARED_FILE_NAME = "FoodRanking";
    public final static String FOOD_STAR_RATE_KEY = "FOOD_STAR";
    public final static String HOT_FOOD_CHECK_KEY = "HOT_FOOD";
    public final static String AMOUNT_OF_FOOD_KEY = "AMOUNT_OF_FOOD";
    public final static String IS_PRICE_FAIR_KEY = "PRICE_FAIR";


    private Button nextPageButton;
    private RatingBar foodQualityRate;
    private RadioGroup hotFoodCheck;
    private SeekBar amountOfFood;
    private Switch priceFair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_ranking);
        this.nextPageButton = findViewById(R.id.nextPage);
        this.foodQualityRate = findViewById(R.id.foodRate);
        this.hotFoodCheck = findViewById(R.id.isFoodHot);
        this.amountOfFood = findViewById(R.id.foodAmount);
        this.priceFair = findViewById(R.id.pricePerFood);

        this.nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodRanking.this, ServiceRanking.class);
                intent.putExtra(FOOD_STAR_RATE_KEY,foodQualityRate.getRating());
                intent.putExtra(HOT_FOOD_CHECK_KEY, hotFoodCheck.getCheckedRadioButtonId() == R.id.foodIsHotRadio);
                intent.putExtra(AMOUNT_OF_FOOD_KEY, amountOfFood.getProgress());
                intent.putExtra(IS_PRICE_FAIR_KEY, priceFair.isChecked());

                //setFoodRanking();

                startActivity(intent);
            }
        });

        retriveRankins();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences lastLocationShared = getSharedPreferences(Dispatcher.LAST_LOCATION_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor lastLocationEditor = lastLocationShared.edit();
        lastLocationEditor.putString(Dispatcher.LAST_CLASS_NAME_KEY, getClass().getName());
        lastLocationEditor.commit();
        setFoodRanking();
    }

    private void setFoodRanking() {
        SharedPreferences foodSharedPrefrences = getSharedPreferences(FOOD_RANKING_SHARED_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor foodEditor = foodSharedPrefrences.edit();
        foodEditor.putFloat(FOOD_STAR_RATE_KEY,foodQualityRate.getRating());
        foodEditor.putBoolean(HOT_FOOD_CHECK_KEY, hotFoodCheck.getCheckedRadioButtonId() == R.id.foodIsHotRadio);
        foodEditor.putInt(AMOUNT_OF_FOOD_KEY, amountOfFood.getProgress());
        foodEditor.putBoolean(IS_PRICE_FAIR_KEY, priceFair.isChecked());
        foodEditor.commit();
    }

    private void retriveRankins() {
        SharedPreferences foodRanking = getSharedPreferences(FOOD_RANKING_SHARED_FILE_NAME, MODE_PRIVATE);
        this.foodQualityRate.setRating(foodRanking.getFloat(FOOD_STAR_RATE_KEY, (float) 0));
        RadioButton hotButton = findViewById(R.id.foodIsHotRadio);
        RadioButton coldButton = findViewById(R.id.foodIsColdRadio);
        hotButton.setChecked(foodRanking.getBoolean(HOT_FOOD_CHECK_KEY, false));
        coldButton.setChecked(!foodRanking.getBoolean(HOT_FOOD_CHECK_KEY, false));
        amountOfFood.setProgress(foodRanking.getInt(AMOUNT_OF_FOOD_KEY, 0));
        priceFair.setChecked(foodRanking.getBoolean(IS_PRICE_FAIR_KEY,false));
    }
}