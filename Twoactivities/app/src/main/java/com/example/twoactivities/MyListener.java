package com.example.twoactivities;

import android.widget.CompoundButton;
import android.widget.Toast;

public class MyListener implements CompoundButton.OnCheckedChangeListener {

    private MainActivity mainActivity;

    public MyListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b) {
            Toast.makeText(this.mainActivity, "Checkbox is checked", Toast.LENGTH_LONG).show();
            this.mainActivity.isTrue = true;
        } else {
            Toast.makeText(this.mainActivity, "Checkbox is unchecked", Toast.LENGTH_LONG).show();
            this.mainActivity.isTrue = false;
        }
    }
}
