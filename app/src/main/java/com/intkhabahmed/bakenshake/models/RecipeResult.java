package com.intkhabahmed.bakenshake.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.intkhabahmed.bakenshake.utils.IngredientTypeConverter;
import com.intkhabahmed.bakenshake.utils.StepTypeConverter;

import java.util.List;

@Entity(tableName = "recipes")
@TypeConverters({IngredientTypeConverter.class, StepTypeConverter.class})
public class RecipeResult {
    @SerializedName("id")
    @ColumnInfo(name = "_id")
    @PrimaryKey
    private int recipeId;
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String recipeName;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    @ColumnInfo(name = "image")
    private String imagePath;
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    private List<Step> steps;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
