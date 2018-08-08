package com.intkhabahmed.bakenshake.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.databinding.ActivityDetailBinding;
import com.intkhabahmed.bakenshake.fragments.RecipeDetailFragment;
import com.intkhabahmed.bakenshake.models.RecipeResult;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Toolbar toolbar = detailBinding.toolbar;
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.recipe))) {
            RecipeResult recipe = intent.getParcelableExtra(getString(R.string.recipe));
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setRecipeResult(recipe);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(detailBinding.fragmentContainerFl.getId(), recipeDetailFragment, null)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.recipe), 1);
    }
}
