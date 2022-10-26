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
import com.migros.mkolay.model.HistoryBasketDetails;

import java.util.List;

public class BasketDetailsViewModel extends RecyclerView.Adapter<BasketDetailsViewModel.ViewHolder> {

    List<HistoryBasketDetails> resultList;

    RecyclerView recyclerView;
    Context context;
    String sign = "";

    Activity activity;


    private final RecyclerOnItemClickListenerInterface listener;


    public interface RecyclerOnItemClickListenerInterface {
        void onItemClick(HistoryBasketDetails item, int position);
    }


    public void setResults(List<HistoryBasketDetails> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    public BasketDetailsViewModel(List<HistoryBasketDetails> resultList, Context context, RecyclerView recyclerView, RecyclerOnItemClickListenerInterface listener, String sign, Activity activity) {
        this.resultList = resultList;
        this.context = context;
        this.recyclerView = recyclerView;
        this.listener = listener;
        this.sign = sign;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BasketDetailsViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_detail_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketDetailsViewModel.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HistoryBasketDetails basketDetailsModel = resultList.get(position);






        holder.productAmount.setText(String.valueOf(basketDetailsModel.getProductAmount()) + " " + context.getResources().getString(R.string.adet_str));
        holder.productPrice.setText(String.valueOf(basketDetailsModel.getProductPrice()) + sign);

        try {
            Glide.with(activity).load(basketDetailsModel.getProductImage()).into(holder.productImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.productWeight.setText(basketDetailsModel.getProductWeight());

        holder.productName.setText(basketDetailsModel.getProductName());





        if(basketDetailsModel.getProductAmount() == 1){
            holder.productAmount.setVisibility(View.GONE);
        }

        if(position == resultList.size() - 1){
            holder.wrapper.setVisibility(View.GONE);
        }









        holder.relativeListItem.setOnClickListener(new View.OnClickListener() {
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
        RelativeLayout detailCardView,relativeListItem;
        TextView productName, productWeight, productPrice;
        Button productAmount;
        ImageView productImage;
        View wrapper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wrapper = (View) itemView.findViewById(R.id.wrapper);
            detailCardView = (RelativeLayout) itemView.findViewById(R.id.detailCardView);
            relativeListItem = (RelativeLayout) itemView.findViewById(R.id.relativeListItem);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productWeight = (TextView) itemView.findViewById(R.id.productWeight);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            productAmount = (Button) itemView.findViewById(R.id.productAmount);


        }
    }


}
