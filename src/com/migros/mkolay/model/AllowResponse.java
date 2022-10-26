package com.migros.mkolay.model;


import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllowResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("allowID")
    @Expose
    private String allowID;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAllowID() {
        return allowID;
    }

    public void setAllowID(String allowID) {
        this.allowID = allowID;
    }
}
