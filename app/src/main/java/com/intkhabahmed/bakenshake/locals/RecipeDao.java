package com.intkhabahmed.bakenshake.locals;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.intkhabahmed.bakenshake.models.RecipeResult;

import java.util.List;

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeResult>> getAllRecipes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRecipes(List<RecipeResult> recipes);

    @Query("SELECT * FROM recipes WHERE _id = :recipeId")
    LiveData<RecipeResult> getRecipeById(int recipeId);
}
