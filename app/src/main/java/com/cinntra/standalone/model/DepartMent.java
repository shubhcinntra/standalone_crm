package com.cinntra.standalone.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "table_Department")
public class DepartMent implements Serializable {
    @NonNull
    @PrimaryKey
    @SerializedName("Name")
    String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
