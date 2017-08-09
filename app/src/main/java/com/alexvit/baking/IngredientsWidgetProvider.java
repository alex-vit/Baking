package com.alexvit.baking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.alexvit.baking.entity.Recipe;
import com.alexvit.baking.util.Text;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                               Recipe recipe, int[] appWidgetIds) {

        for (int id : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, id);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe, int appWidgetId) {

        String title = String.format("%s ingredients:", recipe.name);
        String ingredients = Text.ingredientsToString(recipe.ingredients);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        views.setTextViewText(R.id.widget_title, title);
        views.setTextViewText(R.id.widget_text, ingredients);
        views.setOnClickPendingIntent(R.id.widget_container, recipeIntent(context, recipe));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent listIntent(Context context) {
        Intent intent = new Intent(context, RecipeListActivity.class);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    private static PendingIntent recipeIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.TAG_PARCEL_RECIPE, recipe);

        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
    }

    @Override
    public void onEnabled(final Context context) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        views.setOnClickPendingIntent(R.id.widget_container, listIntent(context));

        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, IngredientsWidgetProvider.class),
                views);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

