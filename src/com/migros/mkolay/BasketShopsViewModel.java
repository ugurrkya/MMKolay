package com.migros.mkolay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.migros.mkolay.model.HistoryBasketShops;

import java.util.List;

public class BasketShopsViewModel extends RecyclerView.Adapter<BasketShopsViewModel.ViewHolder> {

    List<HistoryBasketShops> resultList;

    RecyclerView recyclerView;
    Context context;


    private final RecyclerOnItemClickListenerInterface listener;


    public interface RecyclerOnItemClickListenerInterface {
        void onItemClick(HistoryBasketShops item, int position);
    }


    public void setResults(List<HistoryBasketShops> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    public BasketShopsViewModel(List<HistoryBasketShops> resultList, Context context, RecyclerView recyclerView, RecyclerOnItemClickListenerInterface listener) {
        this.resultList = resultList;
        this.context = context;
        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BasketShopsViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_general_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketShopsViewModel.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HistoryBasketShops basketShopModel = resultList.get(position);






        holder.productShop.setText(basketShopModel.getShop());

        holder.productPrice.setText(String.valueOf(basketShopModel.getBasketPrice()) + basketShopModel.getSign());

        holder.productDate.setText(basketShopModel.getDate());










        holder.relativeListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClick(basketShopModel, position);


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
        RelativeLayout shopCardView,relativeListItem;
        TextView productShop, productDate, productPrice;
        ImageView mark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopCardView = (RelativeLayout) itemView.findViewById(R.id.shopCardView);
            relativeListItem = (RelativeLayout) itemView.findViewById(R.id.relativeListItem);
            productShop = (TextView) itemView.findViewById(R.id.productShop);
            productDate = (TextView) itemView.findViewById(R.id.productDate);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            mark = (ImageView) itemView.findViewById(R.id.mark);


        }
    }


}
