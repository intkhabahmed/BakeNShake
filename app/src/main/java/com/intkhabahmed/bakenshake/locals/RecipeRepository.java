package com.intkhabahmed.bakenshake.locals;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.intkhabahmed.bakenshake.models.RecipeResult;
import com.intkhabahmed.bakenshake.network.ApiClient;
import com.intkhabahmed.bakenshake.network.WebService;
import com.intkhabahmed.bakenshake.utils.AppExecutors;
import com.intkhabahmed.bakenshake.utils.Global;
import com.intkhabahmed.bakenshake.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {
    private static RecipeRepository sInstance;
    private static final Object LOCK = new Object();

    public static RecipeRepository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RecipeRepository();
            }
        }
        return sInstance;
    }

    public LiveData<List<RecipeResult>> getRecipes() {
        final MutableLiveData<List<RecipeResult>> recipes = new MutableLiveData<>();
        if (NetworkUtils.getConnectivityStatus(Global.getInstance())) {
            return Global.getDbInstance().recipeDao().getAllRecipes();
        }
        ApiClient.getInstance().create(WebService.class)
                .getRecipes().enqueue(new Callback<List<RecipeResult>>() {
            @Override
            public void onResponse(@NonNull Call<List<RecipeResult>> call, @NonNull final Response<List<RecipeResult>> response) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Global.getDbInstance().recipeDao().insertRecipes(response.body());
                    }
                });
                recipes.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<RecipeResult>> call, @NonNull Throwable t) {
                call.cancel();
            }
        });
        return recipes;
    }

    public LiveData<RecipeResult> getRecipeById(int recipeId) {
        return Global.getDbInstance().recipeDao().getRecipeById(recipeId);
    }
}
