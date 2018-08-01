package com.intkhabahmed.bakenshake.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.intkhabahmed.bakenshake.locals.RecipeRepository;
import com.intkhabahmed.bakenshake.models.RecipeResult;

import java.util.List;

public class RecipeViewModel extends ViewModel {
    private LiveData<List<RecipeResult>> recipes;

    public RecipeViewModel() {
        if(recipes == null) {
            recipes = RecipeRepository.getInstance().getRecipes();
        }
    }

    public LiveData<List<RecipeResult>> getRecipes() {
        return recipes;
    }
}
