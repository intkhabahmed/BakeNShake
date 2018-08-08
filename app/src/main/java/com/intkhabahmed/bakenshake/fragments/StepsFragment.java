package com.intkhabahmed.bakenshake.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.adapters.StepsFragmentPagerAdapter;
import com.intkhabahmed.bakenshake.databinding.FragmentStepsBinding;
import com.intkhabahmed.bakenshake.models.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsFragment extends Fragment {

    private FragmentStepsBinding mStepsBinding;
    private List<Step> mSteps;

    public StepsFragment() {

    }

    public void setRecipeSteps(List<Step> steps) {
        this.mSteps = steps;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStepsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);
        return mStepsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList(getString(R.string.steps));
        }
        final ViewPager stepsViewPager = mStepsBinding.stepsVp;
        StepsFragmentPagerAdapter stepsPagerAdapter = new StepsFragmentPagerAdapter(getChildFragmentManager(), mSteps);
        stepsViewPager.setAdapter(stepsPagerAdapter);
        stepsViewPager.setOffscreenPageLimit(0);
        mStepsBinding.noOfStepsTv.setText(String.format("%s / %s", stepsViewPager.getCurrentItem(), mSteps.size() - 1));
        stepsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mStepsBinding.noOfStepsTv.setText(String.format("%s / %s", position, mSteps.size() - 1));
                if (position == 0) {
                    mStepsBinding.prevStepTv.setEnabled(false);
                } else if (position == mSteps.size() - 1) {
                    mStepsBinding.nextStepTv.setEnabled(false);
                } else {
                    mStepsBinding.prevStepTv.setEnabled(true);
                    mStepsBinding.nextStepTv.setEnabled(true);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mStepsBinding.prevStepTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepsViewPager.getCurrentItem() != 0) {
                    stepsViewPager.setCurrentItem(stepsViewPager.getCurrentItem() - 1);
                }
            }
        });
        mStepsBinding.nextStepTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepsViewPager.getCurrentItem() != mSteps.size() - 1) {
                    stepsViewPager.setCurrentItem(stepsViewPager.getCurrentItem() + 1);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.steps), (ArrayList<? extends Parcelable>) mSteps);
    }
}
