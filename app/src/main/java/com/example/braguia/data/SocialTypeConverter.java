package com.example.braguia.data;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class SocialTypeConverter {
    @TypeConverter
    public static List<Social> fromString(String value) {
        Type listType = new TypeToken<List<Social>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Social> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
