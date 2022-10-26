package com.migros.mkolay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.migros.mkolay.assistant.AppShared;
import com.migros.mkolay.model.AllowResponse;
import com.migros.mkolay.model.QRCodeResponse;
import com.migros.mkolay.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRActivity extends AppCompatActivity {
    TextView welcomeMessage, qrDesc;
    RelativeLayout newCardRelative,basketHistoryRelative;
    ImageView qrImage;
    QRCodeResponse qrCodeResponse;
    String qrCodeText = "";
    Toolbar toolbar;
    int codeInit = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qractivity);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        initView();



        try {
            createNewQR(String.valueOf(getQRCode(QRActivity.this)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        newCardRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRActivity.this, NewCardActivity.class);
                startActivity(intent);
            }
        });

        basketHistoryRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRActivity.this, BasketHistoryActivity.class);
                startActivity(intent);
            }
        });


        qrImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!qrCodeText.equals("")){
                    try {
                        qrCodeControl(qrCodeText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }


    private void initView() {
        qrDesc = (TextView) findViewById(R.id.qrDesc);
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        newCardRelative = (RelativeLayout) findViewById(R.id.newCardRelative);
        basketHistoryRelative = (RelativeLayout) findViewById(R.id.basketHistoryRelative);
        qrImage = (ImageView) findViewById(R.id.qrImage);
        //formatting basket description text for bold and normal text style
        String text = getResources().getString(R.string.qr_welcome_desc);
        SpannableString ss_str = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        try {
            ss_str.setSpan(boldSpan, 38, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            qrDesc.setText(ss_str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new AppShared(QRActivity.this).setDecor(QRActivity.this,toolbar, getResources().getString(R.string.app_name), getResources().getColor(R.color.qrActivityColor));


    }


    private void createNewQR(String text ) {




        qrCodeText = text;

        String qrText = text;
        MultiFormatWriter formatWriter = new MultiFormatWriter();
        try {
            BitMatrix matrix = formatWriter.encode(qrText, BarcodeFormat.QR_CODE, (int) convertDpToPixel(360.0f, QRActivity.this.getApplicationContext()), (int) convertDpToPixel(390.0f, QRActivity.this.getApplicationContext()));


            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

            Bitmap bitmap = barcodeEncoder.createBitmap(matrix);
            Bitmap myBitmap = Bitmap.createScaledBitmap(
                    bitmap, (int) convertDpToPixel(360.0f, QRActivity.this.getApplicationContext()), (int) convertDpToPixel(390.0f, QRActivity.this.getApplicationContext()),
                    false);


            qrImage.setImageBitmap(myBitmap);




        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public Integer getQRCode(Activity activity) {
        List<String> qrCodesList = new ArrayList<>();
        qrCodesList = Arrays.asList(activity.getResources().getStringArray(R.array.qrCodes));
        Random rand = new Random();
        int code = Integer.parseInt(qrCodesList.get(rand.nextInt(3) + 1));
        while (codeInit == code){
            code = Integer.parseInt(qrCodesList.get(rand.nextInt(3) + 1));
        }
        codeInit = code;


        return code;
    }


    private void qrCodeControl(String text) {




        RetrofitClient.getInstance().getApi(QRActivity.this).doQRControl(text).enqueue(new Callback<QRCodeResponse>() {
            @Override
            public void onResponse(Call<QRCodeResponse> call, Response<QRCodeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        qrCodeResponse = new QRCodeResponse();
                        qrCodeResponse = response.body();


                        if (qrCodeResponse.getStatus() == 101) {
                            try {
                                createNewQR(String.valueOf(getQRCode(QRActivity.this)));

                                Toast.makeText(QRActivity.this, getResources().getString(R.string.status101), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (qrCodeResponse.getStatus() == 1001) {
                            Toast.makeText(QRActivity.this, getResources().getString(R.string.status1001), Toast.LENGTH_LONG).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    throw new RuntimeException(getResources().getString(R.string.QR_CRASH));


                                }
                            }, 200);

                        } else if (qrCodeResponse.getStatus() == 1) {
                            finishAffinity();
                            Intent intent = new Intent(QRActivity.this, ShoppingIntro.class);
                            intent.putExtra(getResources().getString(R.string.qrCodeId), qrCodeResponse.getQrCodeID());
                            startActivity(intent);
                        }


                    } else {

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<QRCodeResponse> call, Throwable t) {
                finishAffinity();
                Intent intent = new Intent(QRActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}