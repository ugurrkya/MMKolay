
package com.migros.mkolay.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BasketDetails implements Serializable
{

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("productWeight")
    @Expose
    private String productWeight;
    @SerializedName("productAmount")
    @Expose
    private Integer productAmount;
    @SerializedName("productPrice")
    @Expose
    private float productPrice;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }
}
