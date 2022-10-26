
package com.migros.mkolay.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BasketResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sign")
    @Expose
    private String sign;
    @SerializedName("basketPrice")
    @Expose
    private float basketPrice;

    @SerializedName("basketDetails")
    @Expose
    private List<BasketDetails> basketDetails = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public List<BasketDetails> getBasketDetails() {
        return basketDetails;
    }

    public void setBasketDetails(List<BasketDetails> basketDetails) {
        this.basketDetails = basketDetails;
    }


}
