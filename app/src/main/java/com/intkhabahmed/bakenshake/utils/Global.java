package com.intkhabahmed.bakenshake.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.locals.AppDatabase;

public class Global extends Application {

    private static Global sGlobalInstance;
    private static SharedPreferences sSharedPreferences;

    public static Global getInstance() {
        return sGlobalInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sGlobalInstance = (Global) getApplicationContext();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public static AppDatabase getDbInstance() {
        return AppDatabase.getInstance(sGlobalInstance);
    }

    public static int getLastOpenedRecipeId() {
        return sSharedPreferences.getInt(sGlobalInstance.getString(R.string.last_opened_recipe_id),
                Integer.parseInt(sGlobalInstance.getString(R.string.default_last_opened_recipe_id)));
    }

    public static void saveLastOpenedRecipeid(int recipeId) {
        sSharedPreferences.edit().putInt(sGlobalInstance.getString(R.string.last_opened_recipe_id), recipeId).apply();
    }
}
