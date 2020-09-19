package com.example.restoranker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceRanking extends AppCompatActivity {
    public final static String SERVICE_RATING_FILE_NAME = "ServiceRating";
    public final static String WAITER_STAR_RANKING_KEY = "WAITER_RANKING";
    public final static String ENV_PLESENT_KEY = "ENV_PLESENT";
    public final static String IS_TOILET_CLEAN_KEY = "TOILET";

    private RatingBar waiterRank;
    private CheckBox isEnvPlesent;
    private RadioGroup isToiletClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ranking);
        final Intent foodRankingIntent = getIntent();
        waiterRank = findViewById(R.id.waiterStarRank);
        isEnvPlesent = findViewById(R.id.isEnvPlesent);
        isToiletClean = findViewById(R.id.cleanToilet);

        Button goToFinalCalc = findViewById(R.id.ToCalculateBtn);
        goToFinalCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allRankings = new Intent(ServiceRanking.this, MainActivity.class);
                allRankings.putExtras(foodRankingIntent);
                allRankings.putExtra(WAITER_STAR_RANKING_KEY, waiterRank.getNumStars());
                allRankings.putExtra(ENV_PLESENT_KEY, isEnvPlesent.isChecked());
                allRankings.putExtra(IS_TOILET_CLEAN_KEY, isToiletClean.getCheckedRadioButtonId() == R.id.toiletIsClean);

                startActivity(allRankings);
            }
        });

        retriveServiceRanking();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences lastLocationShared = getSharedPreferences(Dispatcher.LAST_LOCATION_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor lastLocationEditor = lastLocationShared.edit();
        lastLocationEditor.putString(Dispatcher.LAST_CLASS_NAME_KEY, getClass().getName());
        lastLocationEditor.commit();
        setServiceRanking();
    }

    private void setServiceRanking() {
        SharedPreferences serviceRanking = getSharedPreferences(SERVICE_RATING_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = serviceRanking.edit();
        editor.putFloat(WAITER_STAR_RANKING_KEY, this.waiterRank.getRating());
        editor.putBoolean(ENV_PLESENT_KEY, this.isEnvPlesent.isChecked());
        editor.putBoolean(IS_TOILET_CLEAN_KEY, isToiletClean.getCheckedRadioButtonId() == R.id.toiletIsClean);

        editor.commit();
    }

    private void retriveServiceRanking() {
        SharedPreferences serviceRanking = getSharedPreferences(SERVICE_RATING_FILE_NAME, MODE_PRIVATE);
        this.waiterRank.setRating(serviceRanking.getFloat(WAITER_STAR_RANKING_KEY, (float)0));
        this.isEnvPlesent.setChecked(serviceRanking.getBoolean(ENV_PLESENT_KEY, false));
        RadioButton cleanToilet = findViewById(R.id.toiletIsClean);
        RadioButton dirtyToilet = findViewById(R.id.toiletIsDirty);
        cleanToilet.setChecked(serviceRanking.getBoolean(IS_TOILET_CLEAN_KEY, false));
        dirtyToilet.setChecked(!serviceRanking.getBoolean(IS_TOILET_CLEAN_KEY, false));
    }
}