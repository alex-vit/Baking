package com.alexvit.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.alexvit.baking.entity.Recipe;
import com.alexvit.baking.entity.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailRvAdapter.OnStepClickedListener,
        StepFragment.OnPageNavigationListener {

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

        if (mTwoPane)
            updateFragment(mRecipe.steps.get(0), mRecipe.steps.size());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_PARCEL_RECIPE, mRecipe);
    }

    @Override
    public void onStepClicked(Step step) {
        if (mTwoPane) {
            updateFragment(step, mRecipe.steps.size());
        } else launchStepActivity(mRecipe.steps, step.number);
    }

    @Override
    public void onPrev(int position) {
        int count = mRecipe.steps.size();
        int index = Math.max(0, position - 1);
        Step step = mRecipe.steps.get(index);
        mStepFragment.onDataChanged(step, count);
    }

    @Override
    public void onNext(int position) {
        int count = mRecipe.steps.size();
        int index = Math.min(position + 1, count - 1);
        Step step = mRecipe.steps.get(index);
        mStepFragment.onDataChanged(step, count);
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

    private void updateFragment(Step step, int count) {

        mStepFragment = (StepFragment) getSupportFragmentManager().findFragmentByTag(STEP_FRAGMENT_TAG);

        if (mStepFragment != null) {
            mStepFragment.onDataChanged(step, count);
        } else {
            mStepFragment = StepFragment.newInstance(step, count);
            mStepFragment.setRetainInstance(true);

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(mStepFragmentContainer.getId(), mStepFragment, STEP_FRAGMENT_TAG);
            t.commit();
        }

    }

}
