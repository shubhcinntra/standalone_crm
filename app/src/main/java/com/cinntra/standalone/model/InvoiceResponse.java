package com.cinntra.standalone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InvoiceResponse implements Serializable {
    @SerializedName("data")
    ArrayList<InvoiceNewData> value;
    @SerializedName("error")
    Error error;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<InvoiceNewData> getValue()
    {
        return value;
    }

    public void setValue(ArrayList<InvoiceNewData> value)
    {
        this.value = value;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
