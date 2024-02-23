package com.cinntra.standalone.room;

import androidx.room.TypeConverter;

import com.cinntra.standalone.model.PayMentTerm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ListConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<PayMentTerm> fromString(String value) {
        return gson.fromJson(value, new TypeToken<List<PayMentTerm>>() {}.getType());
    }

    @TypeConverter
    public static String fromList(List<PayMentTerm> list) {
        return gson.toJson(list);
    }
}
