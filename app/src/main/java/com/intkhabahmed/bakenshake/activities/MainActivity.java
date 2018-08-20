package com.intkhabahmed.bakenshake.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.databinding.ActivityMainBinding;
import com.intkhabahmed.bakenshake.fragments.RecipesFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            setupUi();
        }
    }

    private void setupUi() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        if (mMainBinding.secondaryFragmentContainer != null) {
            RecipesFragment recipesFragment = new RecipesFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Intent.EXTRA_TEXT, /*isTwoPaneLayout*/ true);
            recipesFragment.setArguments(bundle);
            mFragmentManager.beginTransaction().replace(mMainBinding.mainFragmentContainer.getId(), recipesFragment)
                    .commit();

        } else {
            mFragmentManager.beginTransaction().replace(mMainBinding.mainFragmentContainer.getId(), new RecipesFragment())
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Intent.EXTRA_TEXT, 1);
    }


}
