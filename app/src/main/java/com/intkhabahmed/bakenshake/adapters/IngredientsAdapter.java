package com.intkhabahmed.bakenshake.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.models.Ingredient;
import com.intkhabahmed.bakenshake.utils.DisplayUtils;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
    private List<Ingredient> mIngredients;

    public IngredientsAdapter() {
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(holder.getAdapterPosition());
        holder.ingredientNameTv.setText(ingredient.getIngredientName());
        holder.quantityTv.setText(DisplayUtils.getFormattedMeasure(ingredient.getMeasure(), ingredient.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) {
            return 0;
        }
        return mIngredients.size();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientNameTv;
        private TextView quantityTv;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientNameTv = itemView.findViewById(R.id.ingredient_name_tv);
            quantityTv = itemView.findViewById(R.id.quantity_tv);
        }
    }
}
