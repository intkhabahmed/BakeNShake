package com.intkhabahmed.bakenshake.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.databinding.FragmentDetailBinding;
import com.intkhabahmed.bakenshake.models.RecipeResult;

public class RecipeDetailFragment extends Fragment {

    private FragmentDetailBinding mFragmentDetailBinding;
    private RecipeResult mRecipe;

    public RecipeDetailFragment() {
    }

    public void setRecipeResult(RecipeResult recipe) {
        this.mRecipe = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
                container, false);
        return mFragmentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(getString(R.string.recipe));
        }
        mFragmentDetailBinding.recipeNameTv.setText(mRecipe != null ? mRecipe.getRecipeName() : null);
        mFragmentDetailBinding.servingsTv.setText(String.valueOf(mRecipe.getServings()));
        mFragmentDetailBinding.ingredientsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setIngredients(mRecipe.getIngredients());
                ingredientsFragment.setRecipeName(mRecipe.getRecipeName());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_fl, ingredientsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        mFragmentDetailBinding.stepsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setRecipeSteps(mRecipe.getSteps());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_fl, stepsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.recipe), mRecipe);
    }
}
