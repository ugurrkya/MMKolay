package com.migros.mkolay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.migros.mkolay.model.BasketDetails;
import com.migros.mkolay.model.HistoryBasketDetails;

import java.util.List;

public class BasketViewModel extends RecyclerView.Adapter<BasketViewModel.ViewHolder> {

    List<BasketDetails> resultList;

    RecyclerView recyclerView;
    Context context;
    String sign = "";

    Activity activity;


    private final RecyclerOnItemClickListenerInterface listener;


    public interface RecyclerOnItemClickListenerInterface {
        void onItemClick(BasketDetails item, int position);
    }


    public void setResults(List<BasketDetails> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    public BasketViewModel(List<BasketDetails> resultList, Context context, RecyclerView recyclerView, RecyclerOnItemClickListenerInterface listener, String sign, Activity activity) {
        this.resultList = resultList;
        this.context = context;
        this.recyclerView = recyclerView;
        this.listener = listener;
        this.sign = sign;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BasketViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewModel.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BasketDetails basketDetailsModel = resultList.get(position);






        holder.basketProductAmount.setText(String.valueOf(basketDetailsModel.getProductAmount()) + " " + context.getResources().getString(R.string.adet_str));
        holder.basketProductPrice.setText(String.valueOf(basketDetailsModel.getProductPrice()) + sign);

        try {
            Glide.with(activity).load(basketDetailsModel.getProductImage()).into(holder.basketProductImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.basketProductWeight.setText(basketDetailsModel.getProductWeight());

        holder.basketProductName.setText(basketDetailsModel.getProductName());





        if(basketDetailsModel.getProductAmount() == 1){
            holder.basketProductAmount.setVisibility(View.GONE);
        }

        if(position == resultList.size() - 1){
            holder.basketWrapper.setVisibility(View.GONE);
        }









        holder.basketListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClick(basketDetailsModel, position);


            }
        });

    }

    @Override
    public int getItemCount() {

        return resultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout basketCardView,basketListItem;
        TextView basketProductName, basketProductWeight, basketProductPrice;
        Button basketProductAmount;
        ImageView basketProductImage;
        View basketWrapper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            basketWrapper = (View) itemView.findViewById(R.id.basketWrapper);
            basketCardView = (RelativeLayout) itemView.findViewById(R.id.basketCardView);
            basketListItem = (RelativeLayout) itemView.findViewById(R.id.basketListItem);
            basketProductName = (TextView) itemView.findViewById(R.id.basketProductName);
            basketProductWeight = (TextView) itemView.findViewById(R.id.basketProductWeight);
            basketProductPrice = (TextView) itemView.findViewById(R.id.basketProductPrice);
            basketProductImage = (ImageView) itemView.findViewById(R.id.basketProductImage);
            basketProductAmount = (Button) itemView.findViewById(R.id.basketProductAmount);


        }
    }


}
