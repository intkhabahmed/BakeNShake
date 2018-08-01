package com.intkhabahmed.bakenshake.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.models.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private List<Step> mSteps;

    public StepsAdapter() {
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mSteps == null) {
            return 0;
        }
        return mSteps.size();
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        StepViewHolder(View itemView) {
            super(itemView);
        }
    }
}
