package com.migros.mkolay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.migros.mkolay.assistant.AppShared;
import com.migros.mkolay.model.AllowResponse;
import com.migros.mkolay.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingIntro extends AppCompatActivity {

    RelativeLayout mainRelative;
    TextView shoppingTitle, shoppingDesc;
    ImageView introImage;
    AllowResponse allowResponse;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_intro);


       initView();
        String qrCodeId = getIntent().getStringExtra(getResources().getString(R.string.qrCodeId));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    isAllowed(qrCodeId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);

    }

    private void initView(){
        mainRelative = (RelativeLayout) findViewById(R.id.mainRelative);
        shoppingTitle = (TextView) findViewById(R.id.shoppingTitle);
        shoppingDesc = (TextView) findViewById(R.id.shoppingDesc);
        introImage = (ImageView) findViewById(R.id.introImage);

        new AppShared(ShoppingIntro.this).setDecor(ShoppingIntro.this,toolbar, getResources().getString(R.string.app_name), getResources().getColor(R.color.shoppingIntroActivityColor));

    }

    private void isAllowed(String id) {
        RetrofitClient.getInstance().getApi(ShoppingIntro.this).getAllow(id).enqueue(new Callback<AllowResponse>() {
            @Override
            public void onResponse(Call<AllowResponse> call, Response<AllowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        allowResponse = new AllowResponse();
                        allowResponse = response.body();


                        if(allowResponse.getStatus() == Integer.parseInt(getResources().getString(R.string.allowValidStatus))){
                            finishAffinity();
                            Intent intent = new Intent(ShoppingIntro.this, BasketActivity.class);
                            startActivity(intent);


                        }else{
                            finishAffinity();
                            Intent intent = new Intent(ShoppingIntro.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(ShoppingIntro.this, String.format(getResources().getString(R.string.deniedStatusPage), allowResponse.getStatus().toString()), Toast.LENGTH_LONG).show();

                        }



                    } else {

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<AllowResponse> call, Throwable t) {
                finishAffinity();
                Intent intent = new Intent(ShoppingIntro.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}