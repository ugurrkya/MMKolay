package com.migros.mkolay.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QRCodeResponse implements Serializable
{

    @SerializedName("qrCodePhoto")
    @Expose
    private String qrCodePhoto;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("qrCodeID")
    @Expose
    private String qrCodeID;

    public String getQrCodePhoto() {
        return qrCodePhoto;
    }

    public void setQrCodePhoto(String qrCodePhoto) {
        this.qrCodePhoto = qrCodePhoto;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQrCodeID() {
        return qrCodeID;
    }

    public void setQrCodeID(String qrCodeID) {
        this.qrCodeID = qrCodeID;
    }
}
