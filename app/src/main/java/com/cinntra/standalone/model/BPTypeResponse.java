package com.cinntra.standalone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BPTypeResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<UTypeData> data = null;
    private final static long serialVersionUID = -6065000172635184482L;

    /**
     * No args constructor for use in serialization
     *
     */
    public BPTypeResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public BPTypeResponse(String message, Integer status, List<UTypeData> data) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UTypeData> getData() {
        return data;
    }

    public void setData(List<UTypeData> data) {
        this.data = data;
    }

}
