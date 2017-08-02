package com.alexvit.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alexvit.baking.loader.RecipesAsyncTaskLoader;
import com.alexvit.baking.entity.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeListRvAdapter.OnRecipeClickedListener {

    private static final String TAG = RecipeListActivity.class.getSimpleName();

    @BindView(R.id.rv_recipe_list)
    RecyclerView mRvRecipes;

    private RecipeListRvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        initRecyclerView();
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipesAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        mAdapter.setRecipes(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        launchRecipeDetail(recipe);
    }

    private void initRecyclerView() {
        mAdapter = new RecipeListRvAdapter(this);
        mRvRecipes.setAdapter(mAdapter);
    }

    private void launchRecipeDetail(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.TAG_PARCEL_RECIPE, recipe);
        startActivity(intent);
    }
}
