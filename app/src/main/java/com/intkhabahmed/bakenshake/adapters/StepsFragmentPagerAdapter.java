package com.intkhabahmed.bakenshake.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.intkhabahmed.bakenshake.fragments.StepItemFragment;
import com.intkhabahmed.bakenshake.models.Step;

import java.util.List;

public class StepsFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Step> mSteps;

    public StepsFragmentPagerAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        StepItemFragment stepItemFragment = new StepItemFragment();
        stepItemFragment.setStep(mSteps.get(position));
        return stepItemFragment;
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }
}
