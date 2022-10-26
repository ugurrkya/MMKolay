package com.migros.mkolay;


import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.migros.mkolay.assistant.AppShared;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    private String token = "";
    public static MainActivity mInst;
    AppShared appShared;

    RelativeLayout mKolayRelative, mKantinRelative;
    TextView mKolayDesc, mKantinDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mInst = this;
        initView();

        appShared = new AppShared(MainActivity.this);

        if(appShared.getGeneralInfo(getResources().getString(R.string.TEMPTOKEN_CONST_ID)).isEmpty()){
            getToken();
        }

        mKantinRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QRActivity.class);
                startActivity(intent);
            }
        });

        mKolayRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.mKolayNotActive), Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if (task.isSuccessful()) {
                    token = task.getResult();

                    appShared.updateGeneralInfo(getResources().getString(R.string.TEMPTOKEN_CONST_ID), token);
                }
            }
        });

    }



    private void initView(){
        mKantinRelative = (RelativeLayout) findViewById(R.id.mKantinRelative);
        mKolayRelative = (RelativeLayout) findViewById(R.id.mKolayRelative);
        mKantinDesc = (TextView) findViewById(R.id.mKantinDesc);
        mKolayDesc = (TextView) findViewById(R.id.mKolayDesc);


        String kantinText = getResources().getString(R.string.mkantin_text);
        SpannableString mKantinStr = new SpannableString(kantinText);
        StyleSpan mKantinboldSpan = new StyleSpan(Typeface.BOLD);

        try {
            mKantinStr.setSpan(mKantinboldSpan, 46, 68, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mKantinDesc.setText(mKantinStr);

        String mKolayText = getResources().getString(R.string.mkolay_text);
        SpannableString mKolayStr = new SpannableString(mKolayText);
        StyleSpan mKolayboldSpan = new StyleSpan(Typeface.BOLD);

        try {
            mKolayStr.setSpan(mKolayboldSpan, 47, 55, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mKolayDesc.setText(mKolayStr);


        new AppShared(MainActivity.this).setDecor(MainActivity.this,toolbar, getResources().getString(R.string.app_name), getResources().getColor(R.color.homePageColor));

    }




    public static MainActivity instance() {
        return mInst;
    }


}