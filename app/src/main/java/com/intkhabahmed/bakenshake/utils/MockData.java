package com.intkhabahmed.bakenshake.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.intkhabahmed.bakenshake.locals.RecipeDao_Impl;
import com.intkhabahmed.bakenshake.models.RecipeResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MockData {
    public static RecipeResult getRecipeById(int recipeId) throws InterruptedException {
        final RecipeResult[] recipes = new RecipeResult[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final LiveData<RecipeResult> resultLiveData = ((RecipeDao_Impl) Global.getDbInstance().recipeDao()).getRecipeById(recipeId);
        Observer<RecipeResult> observer = new Observer<RecipeResult>() {
            @Override
            public void onChanged(@Nullable RecipeResult recipeResult) {
                recipes[0] = recipeResult;
                countDownLatch.countDown();
                resultLiveData.removeObserver(this);
            }
        };
        resultLiveData.observeForever(observer);
        countDownLatch.await(2, TimeUnit.SECONDS);
        return recipes[0];
    }
}
