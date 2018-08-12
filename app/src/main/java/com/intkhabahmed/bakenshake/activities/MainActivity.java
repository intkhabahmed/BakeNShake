package com.intkhabahmed.bakenshake.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.adapters.RecipeAdapter;
import com.intkhabahmed.bakenshake.databinding.ActivityMainBinding;
import com.intkhabahmed.bakenshake.models.RecipeResult;
import com.intkhabahmed.bakenshake.services.RecipeService;
import com.intkhabahmed.bakenshake.utils.Global;
import com.intkhabahmed.bakenshake.viewmodels.RecipeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {

    private ActivityMainBinding mMainBinding;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupUi();
        setupViewModel();
    }

    private void setupViewModel() {
        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<RecipeResult>>() {
            @Override
            public void onChanged(@Nullable List<RecipeResult> recipeResults) {
                if (recipeResults != null) {
                    mMainBinding.mainPb.setVisibility(View.INVISIBLE);
                    mRecipeAdapter.setRecipes(recipeResults);
                } else {
                    mRecipeAdapter.setRecipes(null);
                    mMainBinding.emptyViewContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setupUi() {
        mRecipeAdapter = new RecipeAdapter(this);
        mMainBinding.mainPb.setVisibility(View.VISIBLE);
        mMainBinding.emptyViewContainer.setVisibility(View.GONE);
        RecyclerView recyclerView = mMainBinding.recipesRv;
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onClick(RecipeResult recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(getString(R.string.recipe), recipe);
        Global.saveLastOpenedRecipeid(recipe.getRecipeId());
        RecipeService.startActionUpdateWidget(this);
        startActivity(intent);
    }
}
