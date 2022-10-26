package com.migros.mkolay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.migros.mkolay.assistant.AppShared;
import com.migros.mkolay.model.HistoryBasketDetails;
import com.migros.mkolay.model.HistoryBasketShops;
import com.migros.mkolay.network.RetrofitClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketHistoryActivity extends AppCompatActivity {
    List<HistoryBasketShops> basketShopsList = new ArrayList<HistoryBasketShops>();
    BasketShopsViewModel basketShopsViewModel;
    RecyclerView shopsRecyclerView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_history);


        initView();
    }


    private void initView(){
        shopsRecyclerView = (RecyclerView) findViewById(R.id.shopsRecyclerView);

        LinearLayoutManager linearLayoutManagerOne = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        shopsRecyclerView.setLayoutManager(linearLayoutManagerOne);

        basketShopsViewModel = new BasketShopsViewModel(basketShopsList, this, shopsRecyclerView, new BasketShopsViewModel.RecyclerOnItemClickListenerInterface() {
            @Override
            public void onItemClick(HistoryBasketShops item, int position) {

                List<HistoryBasketDetails> basketDetails = new ArrayList<HistoryBasketDetails>();


                basketDetails = item.getBasketDetails();

                Intent intent = new Intent(BasketHistoryActivity.this, BasketHistoryDetailActivity.class);
                intent.putExtra(getResources().getString(R.string.basketDetailsData),(Serializable) basketDetails);
                intent.putExtra(getResources().getString(R.string.dateData), item.getDate());
                intent.putExtra(getResources().getString(R.string.shopNameData), item.getShop());
                intent.putExtra(getResources().getString(R.string.basketPriceData), item.getBasketPrice());
                intent.putExtra(getResources().getString(R.string.basketDiscountData),item.getBasketDiscount());
                intent.putExtra(getResources().getString(R.string.signData), item.getSign());
                intent.putExtra(getResources().getString(R.string.linkData), item.getLink());


                startActivity(intent);
            }
        });
        shopsRecyclerView.setAdapter(basketShopsViewModel);
        basketShopsViewModel.notifyDataSetChanged();


        new AppShared(BasketHistoryActivity.this).setDecor(BasketHistoryActivity.this,toolbar, getResources().getString(R.string.basketHistoryActivityText), getResources().getColor(R.color.basketHistoryActivityColor));

        try{
            getBasketShops();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

       private void getBasketShops(){
        RetrofitClient.getInstance().getApi(BasketHistoryActivity.this).getBasketShops().enqueue(new Callback<List<HistoryBasketShops>>() {
            @Override
            public void onResponse(Call<List<HistoryBasketShops>> call, Response<List<HistoryBasketShops>> response) {
                   if (response.isSuccessful()){
                    if (response.body() != null){

                        basketShopsList = response.body();


                        basketShopsViewModel.setResults(basketShopsList);


                    }else{
                        basketShopsViewModel.setResults(new ArrayList<HistoryBasketShops>());
                    }

                }else{
                       basketShopsViewModel.setResults(new ArrayList<HistoryBasketShops>());
                   }


            }

            @Override
            public void onFailure(Call<List<HistoryBasketShops>> call, Throwable t) {
                basketShopsViewModel.setResults(new ArrayList<HistoryBasketShops>());
            }
        });
    }
}