package com.intkhabahmed.bakenshake.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.locals.RecipeRepository;
import com.intkhabahmed.bakenshake.models.RecipeResult;
import com.intkhabahmed.bakenshake.utils.Global;
import com.intkhabahmed.bakenshake.widgets.RecipeWidgetProvider;

public class RecipeService extends IntentService {

    private static final String ACTION_UPDATE_RECIPE_WIDGET = "com.intkhabahmed.bakenshake.action.update_widget";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RecipeService() {
        super(RecipeService.class.getSimpleName());
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE_WIDGET.equals(action)) {
                handleActionUpdateWidget(intent);
            }
        }
    }

    private void handleActionUpdateWidget(Intent intent) {
        final LiveData<RecipeResult> recipeResult = RecipeRepository.getInstance().getRecipeById(Global.getLastOpenedRecipeId());
        recipeResult.observeForever(new Observer<RecipeResult>() {
            @Override
            public void onChanged(@Nullable RecipeResult recipe) {
                recipeResult.removeObserver(this);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(RecipeService.this);
                int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(RecipeService.this, RecipeWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.ingredients_lv);
                RecipeWidgetProvider.updateRecipeWidgets(RecipeService.this, appWidgetManager, widgetIds, recipe);
            }
        });
    }
}
