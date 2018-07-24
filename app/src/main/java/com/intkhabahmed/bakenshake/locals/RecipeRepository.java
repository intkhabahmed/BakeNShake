package com.intkhabahmed.bakenshake.locals;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.intkhabahmed.bakenshake.models.RecipeResult;
import com.intkhabahmed.bakenshake.network.ApiClient;
import com.intkhabahmed.bakenshake.network.WebService;

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
        ApiClient.getInstance().create(WebService.class)
                .getRecipes().enqueue(new Callback<List<RecipeResult>>() {
            @Override
            public void onResponse(@NonNull Call<List<RecipeResult>> call, @NonNull Response<List<RecipeResult>> response) {
                recipes.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<RecipeResult>> call, @NonNull Throwable t) {
                call.cancel();
            }
        });
        return recipes;
    }
}
