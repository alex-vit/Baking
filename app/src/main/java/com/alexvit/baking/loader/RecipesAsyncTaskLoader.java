package com.alexvit.baking.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexvit.baking.entity.Recipe;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by alexander.vitjukov on 31.07.2017.
 */

public class RecipesAsyncTaskLoader extends AsyncTaskLoader<List<Recipe>> {

    private static final String TAG = RecipesAsyncTaskLoader.class.getSimpleName();
    List<Recipe> mRecipes;
    private HttpURLConnection connection;

    public RecipesAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mRecipes != null) {
            deliverResult(mRecipes);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Recipe> loadInBackground() {

        String urlString = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        List<Recipe> recipes = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            String jsonString = scanner.next();
            recipes = Arrays.asList(new Gson().fromJson(jsonString, Recipe[].class));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }

        return recipes;
    }

    @Override
    public void deliverResult(List<Recipe> recipes) {
        if (recipes != null) mRecipes = recipes;
        super.deliverResult(recipes);
    }
}
