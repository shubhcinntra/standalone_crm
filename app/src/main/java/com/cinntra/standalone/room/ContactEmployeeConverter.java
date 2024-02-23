package com.cinntra.standalone.room;

import androidx.room.TypeConverter;

import com.cinntra.standalone.model.BPAddress;
import com.cinntra.standalone.model.ContactEmployees;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ContactEmployeeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<ContactEmployees> fromString(String value) {
        return gson.fromJson(value, new TypeToken<List<ContactEmployees>>() {}.getType());
    }

    @TypeConverter
    public static String fromList(List<ContactEmployees> list) {
        return gson.toJson(list);
    }
}
