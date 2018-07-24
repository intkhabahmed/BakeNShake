package com.intkhabahmed.bakenshake.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intkhabahmed.bakenshake.models.Ingredient;

import java.util.List;

public class IngredientTypeConverter {
    @TypeConverter
    public String toString(List<Ingredient> ingredients) {
        Gson gson = new Gson();
        return gson.toJson(ingredients);
    }

    @TypeConverter
    public List<Ingredient> toList(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, new TypeToken<List<Ingredient>>() {
        }.getType());
    }
}
