package com.intkhabahmed.bakenshake.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.databinding.ActivityDetailBinding;
import com.intkhabahmed.bakenshake.models.RecipeResult;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding mDetailBinding;
    private RecipeResult mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.recipe))) {
            mRecipe = (RecipeResult) intent.getSerializableExtra(getString(R.string.recipe));
            setupUi();
        }
    }

    private void setupUi() {
        mDetailBinding.recipeNameTv.setText(mRecipe.getRecipeName());
        mDetailBinding.servingsTv.setText(String.valueOf(mRecipe.getServings()));
    }

    public void showIngredients(View view) {
        Log.v("Detail", "Ingredients clicked");
    }

    public void showSteps(View view) {
        Log.v("Detail", "Steps clicked");
    }
}
