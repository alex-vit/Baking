package com.alexvit.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.alexvit.baking.entity.Recipe;
import com.alexvit.baking.entity.Step;
import com.alexvit.baking.util.Text;

import java.util.ArrayList;
import java.util.List;

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

        mRecipe = getRecipe(savedInstanceState);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(mRecipe.name);
        mIngredients.setText(Text.ingredientsToString(mRecipe.ingredients));

        initRecyclerView();
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
            Log.d(TAG, "got recipe from state");
        } else {
            // starting activity
            recipe = getIntent().getParcelableExtra(TAG_PARCEL_RECIPE);
            Log.d(TAG, "got recipe from extras");
        }

        if (recipe == null) {
            throw new NullPointerException("No recipe was found in state nor extras.");
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

    private void launchStepActivity(List<Step> stepList, int stepNumber) {
        Intent intent = new Intent(this, StepActivity.class);
        ArrayList<Step> steps = new ArrayList<>(stepList);
        intent.putParcelableArrayListExtra(StepActivity.TAG_PARCEL_STEP_LIST, steps);
        intent.putExtra(StepActivity.TAG_PARCEL_STEP_NUMBER, stepNumber);
        startActivity(intent);
    }

}
