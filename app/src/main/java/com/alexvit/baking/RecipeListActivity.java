package com.alexvit.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alexvit.baking.data.RecipeService;
import com.alexvit.baking.entity.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity
        implements RecipeListRvAdapter.OnRecipeClickedListener {

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
        loadRecipes();
    }

    public void onRecipesLoaded(List<Recipe> recipes) {
        mAdapter.setRecipes(recipes);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        launchRecipeDetail(recipe);
    }

    private void initRecyclerView() {
        mAdapter = new RecipeListRvAdapter(this);
        mRvRecipes.setAdapter(mAdapter);
    }

    private void loadRecipes() {
        RecipeService service = ((App) getApplication()).getRecipeService();
        Call<List<Recipe>> call = service.listRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                onRecipesLoaded(recipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

    private void launchRecipeDetail(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.TAG_PARCEL_RECIPE, recipe);
        startActivity(intent);
    }
}
