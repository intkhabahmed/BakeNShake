package com.intkhabahmed.bakenshake.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.activities.DetailActivity;
import com.intkhabahmed.bakenshake.adapters.RecipeAdapter;
import com.intkhabahmed.bakenshake.databinding.FragmentMainBinding;
import com.intkhabahmed.bakenshake.models.RecipeResult;
import com.intkhabahmed.bakenshake.services.RecipeService;
import com.intkhabahmed.bakenshake.utils.AppConstants;
import com.intkhabahmed.bakenshake.utils.Global;
import com.intkhabahmed.bakenshake.viewmodels.RecipeViewModel;

import java.util.List;

public class RecipesFragment extends Fragment implements RecipeAdapter.ItemClickListener {

    private FragmentMainBinding mMainBinding;
    private RecipeAdapter mRecipeAdapter;
    private boolean isTwoPaneLayout;

    public RecipesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isTwoPaneLayout = getArguments().getBoolean(Intent.EXTRA_TEXT);
        }
        if (savedInstanceState != null) {
            isTwoPaneLayout = savedInstanceState.getBoolean(AppConstants.IS_TWO_PANE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return mMainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUi();
        setupViewModel(savedInstanceState);
    }

    private void setupViewModel(final Bundle bundle) {
        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<RecipeResult>>() {
            @Override
            public void onChanged(@Nullable List<RecipeResult> recipeResults) {
                if (recipeResults != null) {
                    mMainBinding.mainPb.setVisibility(View.INVISIBLE);
                    mRecipeAdapter.setRecipes(recipeResults);
                    if (isTwoPaneLayout && bundle == null) {
                        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
                        detailFragment.setRecipeResult(recipeResults.get(0));
                        detailFragment.setArguments(getArguments());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.secondary_fragment_container, detailFragment)
                                .commit();
                    }
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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || isTwoPaneLayout) {
            layoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onClick(RecipeResult recipe) {
        Global.saveLastOpenedRecipeid(recipe.getRecipeId());
        RecipeService.startActionUpdateWidget(getActivity());
        if (isTwoPaneLayout) {
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setRecipeResult(recipe);
            recipeDetailFragment.setArguments(getArguments());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.secondary_fragment_container, recipeDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(getString(R.string.recipe), recipe);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.IS_TWO_PANE, isTwoPaneLayout);
    }
}
