package com.alexvit.baking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.alexvit.baking.entity.Recipe;
import com.alexvit.baking.entity.Step;
import com.alexvit.baking.util.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailRvAdapter.OnStepClickedListener {

    public static final String TAG_PARCEL_RECIPE = "TAG_PARCEL_RECIPE";
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    @BindView(R.id.recipe_ingredients_list)
    TextView mIngredients;

    @BindView(R.id.rv_steps)
    RecyclerView mRecycler;
    private RecipeDetailRvAdapter mAdapter;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipe = getRecipe();

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(mRecipe.name);
        mIngredients.setText(Text.ingredientsToString(mRecipe.ingredients));

        initRecyclerView();
    }

    @Override
    public void onStepClicked(Step step) {
        Log.d(TAG, "Step " + step.number + " clicked");
        /* TODO Implement launching step activity
         * - create launchActivity method
         * - make activity child of this one
         * - pass step list as parcel
         * - implement viewpager for awesomeness
         */
    }

    private Recipe getRecipe() {
        Recipe recipe = getIntent().getParcelableExtra(TAG_PARCEL_RECIPE);
        if (recipe == null) {
            throw new NullPointerException("Parcelable extra Recipe was null.");
        }
        return recipe;
    }

    private void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.setNestedScrollingEnabled(false);
//        mRecycler.setLayoutManager(new NoScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new RecipeDetailRvAdapter(this);
        mAdapter.setSteps(mRecipe.steps);
        mRecycler.setAdapter(mAdapter);
    }

}
