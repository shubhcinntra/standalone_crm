package com.cinntra.standalone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BranchResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Branch> data = null;
    private final static long serialVersionUID = -6065000172635184482L;

    /**
     * No args constructor for use in serialization
     *
     */
    public BranchResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public BranchResponse(String message, Integer status, List<Branch> data) {
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

    public List<Branch> getData() {
        return data;
    }

    public void setData(List<Branch> data) {
        this.data = data;
    }

}
