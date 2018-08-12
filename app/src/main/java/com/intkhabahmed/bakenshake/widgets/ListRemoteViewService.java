package com.intkhabahmed.bakenshake.widgets;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.models.Ingredient;
import com.intkhabahmed.bakenshake.utils.DisplayUtils;
import com.intkhabahmed.bakenshake.utils.Global;

import java.util.List;

public class ListRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(intent);
    }
}

class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> mIngredients;

    ListRemoteViewFactory(Intent intent) {
        if (intent.hasExtra(Global.getInstance().getString(R.string.ingredients))) {
            mIngredients = intent.getBundleExtra(Global.getInstance().getString(R.string.ingredients))
                    .getParcelableArrayList(Global.getInstance().getString(R.string.ingredients));
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(Global.getInstance().getPackageName(), R.layout.ingredient_item);
        Ingredient ingredient = mIngredients.get(position);
        views.setTextViewText(R.id.ingredient_name_tv, ingredient.getIngredientName());
        views.setTextViewText(R.id.quantity_tv, DisplayUtils.getFormattedMeasure(ingredient.getMeasure(), ingredient.getQuantity()));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
