package com.migros.mkolay;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.migros.mkolay.assistant.AppShared;
import com.migros.mkolay.model.BasketDetails;
import com.migros.mkolay.model.BasketResponse;
import com.migros.mkolay.model.HistoryBasketDetails;
import com.migros.mkolay.model.HistoryBasketShops;
import com.migros.mkolay.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketActivity extends AppCompatActivity {
    TextView basketDesc;
    public static BasketActivity basketActivity;
    Button homePageButton;
    private BottomSheetBehavior mBottomSheetBehaviour;

    List<BasketDetails> basketDetails = new ArrayList<BasketDetails>();
    BasketResponse basketResponse = new BasketResponse();
    BasketViewModel basketViewModel;
    RecyclerView recyclerView;
    TextView totalPrice;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        basketActivity = this;
        initView();

        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                Intent intent = new Intent(BasketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initView(){
        basketDesc = (TextView) findViewById(R.id.basketDesc);
        homePageButton = (Button) findViewById(R.id.homePageButton);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        //formatting basket description text for bold and normal text style
        String text = getResources().getString(R.string.basket_desc);
        SpannableString ss_str = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        try{
            ss_str.setSpan(boldSpan, 20, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        basketDesc.setText(ss_str);


        new AppShared(BasketActivity.this).setDecor(BasketActivity.this,toolbar, getResources().getString(R.string.app_name), getResources().getColor(R.color.basketActivityColor));

        LinearLayoutManager linearLayoutManagerOne = new LinearLayoutManager(BasketActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManagerOne);


        basketViewModel = new BasketViewModel(basketDetails, BasketActivity.this, recyclerView, new BasketViewModel.RecyclerOnItemClickListenerInterface() {
            @Override
            public void onItemClick(BasketDetails item, int position) {

            }
        },"",BasketActivity.this);
        recyclerView.setAdapter(basketViewModel);
        basketViewModel.notifyDataSetChanged();

        try{
            getBasketList();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mBottomSheetBehaviour = BottomSheetBehavior.from(nestedScrollView);




    }

    public static BasketActivity instance() {
        return basketActivity;
    }


    private void getBasketList(){


        RetrofitClient.getInstance().getApi(BasketActivity.this).getBasketList().enqueue(new Callback<BasketResponse>() {
            @Override
            public void onResponse(Call<BasketResponse> call, Response<BasketResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){

                        basketResponse = response.body();
                        basketDetails = basketResponse.getBasketDetails();

                        totalPrice.setText(String.valueOf(basketResponse.getBasketPrice()) + basketResponse.getSign());
                        basketViewModel = new BasketViewModel(basketDetails, BasketActivity.this, recyclerView, new BasketViewModel.RecyclerOnItemClickListenerInterface() {
                            @Override
                            public void onItemClick(BasketDetails item, int position) {

                            }
                        },basketResponse.getSign(),BasketActivity.this);
                        recyclerView.setAdapter(basketViewModel);


                        basketViewModel.setResults(basketDetails);


                    }else{
                        basketViewModel.setResults(new ArrayList<BasketDetails>());
                    }

                }else{
                    basketViewModel.setResults(new ArrayList<BasketDetails>());
                }


            }

            @Override
            public void onFailure(Call<BasketResponse> call, Throwable t) {
                basketViewModel.setResults(new ArrayList<BasketDetails>());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Intent intent = new Intent(BasketActivity.this, MainActivity.class);
        startActivity(intent);
    }
}