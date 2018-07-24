package com.intkhabahmed.bakenshake.network;

import com.intkhabahmed.bakenshake.models.RecipeResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("android-baking-app-json")
    Call<List<RecipeResult>> getRecipes();
}
