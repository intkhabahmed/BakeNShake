package com.intkhabahmed.bakenshake.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intkhabahmed.bakenshake.models.Step;

import java.util.List;

public class StepTypeConverter {
    @TypeConverter
    public String toString(List<Step> ingredients) {
        Gson gson = new Gson();
        return gson.toJson(ingredients);
    }

    @TypeConverter
    public List<Step> toList(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, new TypeToken<List<Step>>() {
        }.getType());
    }
}
