package com.alexvit.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.baking.entity.Recipe;
import com.alexvit.baking.entity.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailRvAdapter.OnStepClickedListener {

    public static final String TAG_PARCEL_RECIPE = "TAG_PARCEL_RECIPE";

    @SuppressWarnings("unused")
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    RecipeDetailFragment mRecipeFragment;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mRecipe = getRecipe(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        getSupportActionBar().setTitle(mRecipe.name);
        mRecipeFragment = (RecipeDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_detail);
        mRecipeFragment.displayRecipe(mRecipe);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_PARCEL_RECIPE, mRecipe);
    }

    @Override
    public void onStepClicked(Step step) {
        launchStepActivity(mRecipe.steps, step.number);
    }

    private Recipe getRecipe(Bundle state) {

        Recipe recipe = null;

        if (state != null) {
            // restoring activity
            recipe = state.getParcelable(TAG_PARCEL_RECIPE);
        } else {
            // starting activity
            recipe = getIntent().getParcelableExtra(TAG_PARCEL_RECIPE);
        }

        if (recipe == null) {
            throw new NullPointerException("No recipe was found in state nor extras.");
        }
        return recipe;
    }


    private void launchStepActivity(List<Step> stepList, int stepNumber) {
        Intent intent = new Intent(this, StepActivity.class);
        ArrayList<Step> steps = new ArrayList<>(stepList);
        intent.putParcelableArrayListExtra(StepActivity.TAG_PARCEL_STEP_LIST, steps);
        intent.putExtra(StepActivity.TAG_PARCEL_STEP_NUMBER, stepNumber);
        startActivity(intent);
    }

}
