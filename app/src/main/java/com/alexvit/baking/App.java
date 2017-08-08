package com.alexvit.baking;

import android.app.Application;

import com.alexvit.baking.data.RecipeService;
import com.alexvit.baking.data.RecipeServiceBuilder;

/**
 * Created by alexander.vitjukov on 08.08.2017.
 */

public class App extends Application {

    private RecipeService recipeService;

    @Override
    public void onCreate() {
        super.onCreate();

        this.recipeService = new RecipeServiceBuilder(getApplicationContext()).get();
    }

    public RecipeService getRecipeService() {
        return recipeService;
    }
}
