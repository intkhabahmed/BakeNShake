package com.intkhabahmed.bakenshake.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.models.RecipeResult;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<RecipeResult> mRecipes;
    private ItemClickListener mItemClickListener;
    public RecipeAdapter(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(RecipeResult recipe);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeResult recipe = mRecipes.get(holder.getAdapterPosition());
        holder.recipeNameTv.setText(recipe.getRecipeName());
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        }
        return mRecipes.size();
    }

    public void setRecipes(List<RecipeResult> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView recipeNameTv;

        RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTv = itemView.findViewById(R.id.recipe_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onClick(mRecipes.get(getAdapterPosition()));
        }
    }
}
