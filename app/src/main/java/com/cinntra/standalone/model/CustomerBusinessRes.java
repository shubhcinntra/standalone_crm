package com.cinntra.standalone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomerBusinessRes implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<BusinessPartnerData> data = null;
    private final static long serialVersionUID = -637633141134967407L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CustomerBusinessRes() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public CustomerBusinessRes(String message, String status, List<BusinessPartnerData> data) {
        super();
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BusinessPartnerData> getData() {
        return data;
    }

    public void setData(List<BusinessPartnerData> data) {
        this.data = data;
    }

}
