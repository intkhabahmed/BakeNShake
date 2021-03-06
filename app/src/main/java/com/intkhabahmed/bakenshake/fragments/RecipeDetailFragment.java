package com.intkhabahmed.bakenshake.fragments;

import android.content.Intent;
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
import com.intkhabahmed.bakenshake.utils.AppConstants;

public class RecipeDetailFragment extends Fragment {

    private FragmentDetailBinding mFragmentDetailBinding;
    private RecipeResult mRecipe;
    private boolean isTwoPaneLayout;

    public RecipeDetailFragment() {
    }

    public void setRecipeResult(RecipeResult recipe) {
        this.mRecipe = recipe;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isTwoPaneLayout = getArguments().getBoolean(Intent.EXTRA_TEXT);
        }
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(getString(R.string.recipe));
            isTwoPaneLayout = savedInstanceState.getBoolean(AppConstants.IS_TWO_PANE);
        }
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
        setupUi(mRecipe);
    }

    private void setupUi(@NonNull final RecipeResult recipe) {
        mFragmentDetailBinding.recipeNameTv.setText(recipe.getRecipeName());
        mFragmentDetailBinding.servingsTv.setText(String.valueOf(recipe.getServings()));
        mFragmentDetailBinding.ingredientsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setIngredients(recipe.getIngredients());
                ingredientsFragment.setRecipeName(recipe.getRecipeName());
                getActivity().getSupportFragmentManager().beginTransaction().replace(isTwoPaneLayout ?
                        R.id.secondary_fragment_container : R.id.fragment_container_fl, ingredientsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        mFragmentDetailBinding.stepsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setRecipeSteps(recipe.getSteps());
                getActivity().getSupportFragmentManager().beginTransaction().replace(isTwoPaneLayout ?
                        R.id.secondary_fragment_container : R.id.fragment_container_fl, stepsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.recipe), mRecipe);
        outState.putBoolean(AppConstants.IS_TWO_PANE, isTwoPaneLayout);
    }
}
