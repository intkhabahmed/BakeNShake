package com.intkhabahmed.bakenshake.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.activities.DetailActivity;
import com.intkhabahmed.bakenshake.models.RecipeResult;
import com.intkhabahmed.bakenshake.services.RecipeService;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RecipeResult recipe) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.recipe_name_tv, recipe.getRecipeName());
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(context.getString(R.string.recipe), recipe);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                // add all of DetailsActivity's parents to the stack,
                // followed by DetailsActivity itself
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.show_detail_btn, pendingIntent);

        Intent listIntent = new Intent(context, ListRemoteViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId); intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(context.getString(R.string.ingredients),
                (ArrayList<? extends Parcelable>) recipe.getIngredients());
        listIntent.putExtra(context.getString(R.string.ingredients), bundle);
        views.setRemoteAdapter(R.id.ingredients_lv, listIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RecipeService.startActionUpdateWidget(context);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, RecipeResult recipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RecipeService.startActionUpdateWidget(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

