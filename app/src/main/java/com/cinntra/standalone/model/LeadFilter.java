package com.cinntra.standalone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LeadFilter implements Serializable {
    @SerializedName("assignedTo")
    @Expose
    private String assignedTo;
    @SerializedName("employeeId")
    @Expose
    private String employeeId;
    @SerializedName("leadType")
    @Expose
    private String leadType;
    @SerializedName("maxItem")
    @Expose
    private int MaxItem;
    @SerializedName("PageNo")
    @Expose
    private int PageNo;


    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeadType() {
        return leadType;
    }

    public void setLeadType(String leadType) {
        this.leadType = leadType;
    }

    public int getMaxItem() {
        return MaxItem;
    }

    public void setMaxItem(int maxItem) {
        MaxItem = maxItem;
    }

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }
}
