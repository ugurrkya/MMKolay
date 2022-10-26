package com.migros.mkolay.network;

import com.migros.mkolay.model.AllowResponse;
import com.migros.mkolay.model.BasketResponse;
import com.migros.mkolay.model.HistoryBasketShops;
import com.migros.mkolay.model.QRCodeResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface APIInterface {
    /**
     * page allow value - if 2, goes to next page
     * @param allow
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("allow/{allowID}")
    Call<AllowResponse> getAllow(@Path("allowID") String allow);

    /**
     * controls the QR generated
     * @param qrCode
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("qrcode/{qrCodeID}")
    Call<QRCodeResponse> doQRControl(@Path("qrCodeID") String qrCode);


    /**
     * retrieves market history belongs to the user
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("markethistory")
    Call<List<HistoryBasketShops>> getBasketShops();


    /**
     * returns current basket of the user
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("basket")
    Call<BasketResponse> getBasketList();

}
