package com.example.braguia.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PinTypeConverter {
    @TypeConverter
    public static Pin fromString(String value) {
        Type listType = new TypeToken<Pin>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(Pin pin) {
        Gson gson = new Gson();
        return gson.toJson(pin);
    }
}
