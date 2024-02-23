package com.cinntra.standalone.room;

import androidx.room.TypeConverter;

import com.cinntra.standalone.model.BPAddress;
import com.cinntra.standalone.model.UTypeData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BpAddressConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<BPAddress> fromString(String value) {
        return gson.fromJson(value, new TypeToken<List<BPAddress>>() {}.getType());
    }

    @TypeConverter
    public static String fromList(List<BPAddress> list) {
        return gson.toJson(list);
    }
}
