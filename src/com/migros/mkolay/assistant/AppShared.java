package com.migros.mkolay.assistant;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.migros.mkolay.R;

import java.util.Objects;

public class AppShared {
    Activity ac;
    SharedPreferences sha;



    public AppShared(Activity ac) {
        this.ac = ac;
        if (ac != null) {

            String app;
            if (ac.getApplicationContext() != null) {

                app = ac.getApplicationContext().getPackageName();
            } else {
                app = ac.getPackageName();

            }

            sha = ac.getApplicationContext().getSharedPreferences(app, ac.MODE_PRIVATE);

        }

    }

    public void updateGeneralInfo(String info, String value) {
        final String finalInfo = info;
        final SharedPreferences sha = ac.getApplicationContext()
                .getSharedPreferences(ac.getPackageName(), ac.MODE_PRIVATE);
        SharedPreferences.Editor edit = sha.edit();
        edit.putString(finalInfo, value);
        edit.apply();
    }
    public String getGeneralInfo(String info) {
        final String finalInfo = info;
        final SharedPreferences sha = ac.getApplicationContext()
                .getSharedPreferences(ac.getPackageName(), ac.MODE_PRIVATE);
        String record = sha.getString(finalInfo, "");
        if (record.equals("")) {
            record = "";
        }
        return record;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setDecor(AppCompatActivity ac, Toolbar toolbar, String screenName, int color) {
        try {
            if (Build.VERSION.SDK_INT >= 27) {
                ac.getWindow().setStatusBarColor(ContextCompat.getColor(ac, color)); //status bar or the time bar at the top

            }
            if (Build.VERSION.SDK_INT >= 27) {
                View decorView = ac.getWindow().getDecorView();
                decorView.setSystemUiVisibility(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS |
                        SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar = ac.findViewById(R.id.my_awesome_toolbar);


        TextView toolbar_txt = (TextView) ac.findViewById(R.id.custom_title_action);
        toolbar_txt.setText(screenName);

        toolbar.setBackgroundColor(color);
        ac.setSupportActionBar(toolbar);


        try {
            ac.getActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {
           ac.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


    }



}
