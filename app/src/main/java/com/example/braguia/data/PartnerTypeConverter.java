package com.example.braguia.data;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class PartnerTypeConverter {
    @TypeConverter
    public static List<Partner> fromString(String value) {
        Type listType = new TypeToken<List<Partner>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Partner> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
