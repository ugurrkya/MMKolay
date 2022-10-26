package com.migros.mkolay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.migros.mkolay.assistant.AppShared;
import com.migros.mkolay.model.HistoryBasketDetails;

import java.util.ArrayList;
import java.util.List;

public class BasketHistoryDetailActivity extends AppCompatActivity {
    List<HistoryBasketDetails> basketDetails = new ArrayList<HistoryBasketDetails>();
    String linkValue = "";
    String signValue = "";
    String dateValue = "";
    String shopNameValue = "";
    float basketPriceValue = 0.0f;
    float discountValue = 0.0f;
    TextView date,shopName,basketPrice,discount,totalPrice;
    ImageView pdfImage;


    RecyclerView detailsRecyclerView;
    BasketDetailsViewModel basketDetailsViewModel;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_history_detail);
        initScreen();
        getIntentValues();

        pdfImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!linkValue.isEmpty()){
                    Intent pdfIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkValue));
                    startActivity(pdfIntent);
                }
            }
        });

    }
    private void initScreen(){
        detailsRecyclerView = (RecyclerView) findViewById(R.id.detailsRecyclerView);
        pdfImage = (ImageView) findViewById(R.id.pdfImage);
        date = (TextView) findViewById(R.id.date);
        shopName = (TextView) findViewById(R.id.shopName);
        basketPrice = (TextView) findViewById(R.id.basketPrice);
        discount = (TextView) findViewById(R.id.discount);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
    }

    @SuppressLint("SetTextI18n")
    private void getIntentValues(){
        shopNameValue = getIntent().getStringExtra(getResources().getString(R.string.shopNameData));
        signValue = getIntent().getStringExtra(getResources().getString(R.string.signData));
        discountValue = getIntent().getFloatExtra(getResources().getString(R.string.basketDiscountData),0.0f);
        basketPriceValue = getIntent().getFloatExtra(getResources().getString(R.string.basketPriceData), 0.0f);
        dateValue = getIntent().getStringExtra(getResources().getString(R.string.dateData));
        linkValue = getIntent().getStringExtra(getResources().getString(R.string.linkData));
        basketDetails = (ArrayList<HistoryBasketDetails>) getIntent().getSerializableExtra(getResources().getString(R.string.basketDetailsData));


        date.setText(dateValue);
        shopName.setText(shopNameValue);
        discount.setText(getResources().getString(R.string.minus) + String.valueOf(discountValue) + signValue);
        basketPrice.setText(String.valueOf(basketPriceValue) + signValue);

        totalPrice.setText(String.valueOf(basketPriceValue-discountValue) + signValue);


        new AppShared(BasketHistoryDetailActivity.this).setDecor(BasketHistoryDetailActivity.this,toolbar, getResources().getString(R.string.basketHistoryActivityText), getResources().getColor(R.color.basketHistoryDetailActivityColor));



        LinearLayoutManager linearLayoutManagerOne = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detailsRecyclerView.setLayoutManager(linearLayoutManagerOne);


        basketDetailsViewModel = new BasketDetailsViewModel(basketDetails, this, detailsRecyclerView, new BasketDetailsViewModel.RecyclerOnItemClickListenerInterface() {
            @Override
            public void onItemClick(HistoryBasketDetails item, int position) {

            }
        },signValue,BasketHistoryDetailActivity.this);
        detailsRecyclerView.setAdapter(basketDetailsViewModel);
        basketDetailsViewModel.notifyDataSetChanged();


    }
}