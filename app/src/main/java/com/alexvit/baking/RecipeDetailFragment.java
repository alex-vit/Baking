package com.alexvit.baking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvit.baking.entity.Recipe;
import com.alexvit.baking.util.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandrs Vitjukovs on 8/5/2017.
 */

public class RecipeDetailFragment extends Fragment {

    @BindView(R.id.recipe_ingredients_list)
    TextView mIngredients;
    @BindView(R.id.rv_steps)
    RecyclerView mRecycler;

    private RecipeDetailRvAdapter mAdapter;
    private RecipeDetailRvAdapter.OnStepClickedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (RecipeDetailRvAdapter.OnStepClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);

        initRecyclerView();

        return rootView;
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public void displayRecipe(Recipe recipe) {
        mIngredients.setText(Text.ingredientsToString(recipe.ingredients));
        mAdapter.setSteps(recipe.steps);
    }

    private void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecycler.setNestedScrollingEnabled(false);

        mAdapter = new RecipeDetailRvAdapter(mListener);
        mRecycler.setAdapter(mAdapter);
    }

}
