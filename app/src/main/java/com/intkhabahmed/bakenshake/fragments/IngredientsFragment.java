package com.intkhabahmed.bakenshake.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.adapters.IngredientsAdapter;
import com.intkhabahmed.bakenshake.databinding.FragmentIngredientsBinding;
import com.intkhabahmed.bakenshake.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding mIngredientsBinding;
    private List<Ingredient> mIngredients;
    private String mRecipeName;

    public IngredientsFragment() {
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mIngredientsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false);
        return mIngredientsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getParcelableArrayList(getString(R.string.ingredients));
            mRecipeName = savedInstanceState.getString(getString(R.string.recipe));
        }
        mIngredientsBinding.recipeNameTv.setText(mRecipeName);
        RecyclerView recyclerView = mIngredientsBinding.ingredientsRv;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
        ingredientsAdapter.setIngredients(mIngredients);
        recyclerView.setAdapter(ingredientsAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.recipe), mRecipeName);
        outState.putParcelableArrayList(getString(R.string.ingredients), (ArrayList<? extends Parcelable>) mIngredients);
    }
}
