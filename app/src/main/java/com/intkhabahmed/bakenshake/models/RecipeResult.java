package com.intkhabahmed.bakenshake.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.intkhabahmed.bakenshake.utils.IngredientTypeConverter;
import com.intkhabahmed.bakenshake.utils.StepTypeConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "recipes")
@TypeConverters({IngredientTypeConverter.class, StepTypeConverter.class})
public class RecipeResult implements Parcelable {
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

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public RecipeResult createFromParcel(Parcel source) {
            return new RecipeResult(source);
        }

        @Override
        public RecipeResult[] newArray(int size) {
            return new RecipeResult[size];
        }
    };

    public RecipeResult(int recipeId, String recipeName, int servings, String imagePath, List<Ingredient> ingredients, List<Step> steps) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.servings = servings;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @SuppressWarnings("unchecked")
    private RecipeResult(Parcel source) {
        this.recipeId = source.readInt();
        this.recipeName = source.readString();
        this.servings = source.readInt();
        this.imagePath = source.readString();
        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        source.readTypedList(ingredients, Ingredient.CREATOR);
        source.readTypedList(steps, Step.CREATOR);
        this.ingredients = ingredients;
        this.steps = steps;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeInt(servings);
        dest.writeString(imagePath);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }
}
