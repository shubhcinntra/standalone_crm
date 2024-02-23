package com.cinntra.standalone.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DeliveryResponse implements Serializable {
    @SerializedName("value")
    ArrayList<Delivery> value;

    public ArrayList<Delivery> getValue()
     {
     return value;
     }
    public void setValue(ArrayList<Delivery> value)
      {
    this.value = value;
      }
}
