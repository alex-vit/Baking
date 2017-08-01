package com.alexvit.baking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alexvit.baking.model.Recipe;
import com.alexvit.baking.util.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String TAG_PARCEL_RECIPE = "TAG_PARCEL_RECIPE";
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    @BindView(R.id.recipe_ingredients_list)
    TextView mIngredients;

    @BindView(R.id.rv_steps)
    RecyclerView mRecycler;


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

    private Recipe getRecipe() {
        Recipe recipe = getIntent().getParcelableExtra(TAG_PARCEL_RECIPE);
        if (recipe == null) {
            throw new NullPointerException("Parcelable extra Recipe was null.");
        }
        return recipe;
    }

    private void initRecyclerView() {
    }
}
