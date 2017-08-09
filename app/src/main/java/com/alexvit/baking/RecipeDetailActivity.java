package com.alexvit.baking;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.alexvit.baking.entity.Recipe;
import com.alexvit.baking.entity.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alexvit.baking.IngredientsWidgetProvider.updateIngredientWidgets;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailRvAdapter.OnStepClickedListener {

    public static final String TAG_PARCEL_RECIPE = "TAG_PARCEL_RECIPE";

    private static final String STEP_FRAGMENT_TAG = "STEP_FRAGMENT_TAG";
    @SuppressWarnings("unused")
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    @Nullable
    @BindView(R.id.step_fragment_container)
    LinearLayout mStepFragmentContainer;

    RecipeDetailFragment mRecipeFragment;
    StepFragment mStepFragment;

    private Recipe mRecipe;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mRecipe = getRecipe(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(mRecipe.name);
        mRecipeFragment = (RecipeDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_detail);
        mRecipeFragment.displayRecipe(mRecipe);

        mTwoPane = (mStepFragmentContainer != null);

        if (mTwoPane) {
            initStepFragment();
        }

        updateWidgets(mRecipe);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_PARCEL_RECIPE, mRecipe);
    }

    @Override
    public void onStepClicked(int position) {
        if (mTwoPane) {
            mStepFragment.setPosition(position);
        } else launchStepActivity(mRecipe.steps, position);
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


    private void launchStepActivity(List<Step> steps, int position) {
        Intent intent = new Intent(this, StepActivity.class);
        intent.putParcelableArrayListExtra(StepActivity.TAG_PARCEL_STEPS, new ArrayList<>(steps));
        intent.putExtra(StepActivity.TAG_PARCEL_POSITION, position);
        startActivity(intent);
    }

    private void updateWidgets(Recipe recipe) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] ids = manager.getAppWidgetIds(
                new ComponentName(this, IngredientsWidgetProvider.class));

        updateIngredientWidgets(this, manager, recipe, ids);
    }

    private void initStepFragment() {
        mStepFragment = (StepFragment) getSupportFragmentManager()
                .findFragmentByTag(STEP_FRAGMENT_TAG);

        if (mStepFragment == null) {
            mStepFragment = StepFragment.newInstance(mRecipe.steps);

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(mStepFragmentContainer.getId(), mStepFragment, STEP_FRAGMENT_TAG);
            t.commit();
        }
    }
}
