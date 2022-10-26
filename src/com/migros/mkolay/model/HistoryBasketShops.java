
package com.migros.mkolay.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HistoryBasketShops implements Serializable {



    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("shop")
    @Expose
    private String shop;
    @SerializedName("sign")
    @Expose
    private String sign;
    @SerializedName("basketPrice")
    @Expose
    private float basketPrice;
    @SerializedName("basketDiscount")
    @Expose
    private float basketDiscount;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("basketDetails")
    @Expose
    private List<HistoryBasketDetails> basketDetails = null;


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public float getBasketPrice() {
        return basketPrice;
    }

    public void setBasketPrice(float basketPrice) {
        this.basketPrice = basketPrice;
    }

    public float getBasketDiscount() {
        return basketDiscount;
    }

    public void setBasketDiscount(float basketDiscount) {
        this.basketDiscount = basketDiscount;
    }

    public List<HistoryBasketDetails> getBasketDetails() {
        return basketDetails;
    }

    public void setBasketDetails(List<HistoryBasketDetails> basketDetails) {
        this.basketDetails = basketDetails;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
