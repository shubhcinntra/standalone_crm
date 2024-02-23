package com.cinntra.standalone.newapimodel;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadValue implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("numOfEmployee")
    @Expose
    private Integer numOfEmployee;
    @SerializedName("turnover")
    @Expose
    private String turnover;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("contactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("productInterest")
    @Expose
    private String productInterest;
    @SerializedName("assignedTo")
    @Expose
    private AssignedTo assignedTo;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("employeeId")
    @Expose
    private EmployeeId employeeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("leadType")
    @Expose
    private String leadType;
    @SerializedName("junk")
    @Expose
    private Integer junk;
    public final static Creator<LeadValue> CREATOR = new Creator<LeadValue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LeadValue createFromParcel(android.os.Parcel in) {
            return new LeadValue(in);
        }

        public LeadValue[] newArray(int size) {
            return (new LeadValue[size]);
        }

    }
            ;

    protected LeadValue(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.companyName = ((String) in.readValue((String.class.getClassLoader())));
        this.numOfEmployee = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.turnover = ((String) in.readValue((String.class.getClassLoader())));
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.contactPerson = ((String) in.readValue((String.class.getClassLoader())));
        this.designation = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.productInterest = ((String) in.readValue((String.class.getClassLoader())));
        this.assignedTo = ((AssignedTo) in.readValue((AssignedTo.class.getClassLoader())));
        this.timestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.employeeId = ((EmployeeId) in.readValue((EmployeeId.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.leadType = ((String) in.readValue((String.class.getClassLoader())));
        this.junk = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public LeadValue() {
    }

    /**
     *
     * @param date
     * @param numOfEmployee
     * @param productInterest
     * @param companyName
     * @param contactPerson
     * @param employeeId
     * @param source
     * @param message
     * @param assignedTo
     * @param phoneNumber
     * @param location
     * @param id
     * @param designation
     * @param turnover
     * @param email
     * @param timestamp
     */
    public LeadValue(Integer id, String date, String location, String companyName, Integer numOfEmployee, String turnover, String source, String contactPerson, String designation, String phoneNumber, String message, String email, String productInterest, AssignedTo assignedTo, String timestamp, EmployeeId employeeId, String status, String leadType) {
        super();
        this.id = id;
        this.date = date;
        this.location = location;
        this.companyName = companyName;
        this.numOfEmployee = numOfEmployee;
        this.turnover = turnover;
        this.source = source;
        this.contactPerson = contactPerson;
        this.designation = designation;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.email = email;
        this.productInterest = productInterest;
        this.assignedTo = assignedTo;
        this.timestamp = timestamp;
        this.employeeId = employeeId;
        this.status = status;
        this.leadType = leadType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getNumOfEmployee() {
        return numOfEmployee;
    }

    public void setNumOfEmployee(Integer numOfEmployee) {
        this.numOfEmployee = numOfEmployee;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductInterest() {
        return productInterest;
    }

    public void setProductInterest(String productInterest) {
        this.productInterest = productInterest;
    }

    public AssignedTo getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedTo assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getLeadType() {
        return leadType;
    }

    public void setLeadType(String leadType) {
        this.leadType = leadType;
    }

    public Integer getJunk() {
        return junk;
    }

    public void setJunk(Integer junk) {
        this.junk = junk;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(date);
        dest.writeValue(location);
        dest.writeValue(companyName);
        dest.writeValue(numOfEmployee);
        dest.writeValue(turnover);
        dest.writeValue(source);
        dest.writeValue(contactPerson);
        dest.writeValue(designation);
        dest.writeValue(phoneNumber);
        dest.writeValue(message);
        dest.writeValue(email);
        dest.writeValue(productInterest);
        dest.writeValue(assignedTo);
        dest.writeValue(timestamp);
        dest.writeValue(employeeId);
        dest.writeValue(status);
        dest.writeValue(leadType);
        dest.writeValue(junk);
    }

    public int describeContents() {
        return 0;
    }

}
