package com.alexvit.baking;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvit.baking.model.Recipe;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexander.vitjukov on 31.07.2017.
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.MyViewHolder> {

    private List<Recipe> mRecipes = null;
    private OnRecipeClickedListener mListener;

    public RecipeRecyclerViewAdapter(OnRecipeClickedListener listener) {
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recipe, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Recipe recipe = getData(position);
        holder.name.setText(recipe.name);

        if (recipe.image != null && !recipe.image.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(recipe.image)
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return (mRecipes == null) ? 0 : mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    private Recipe getData(int position) {
        return mRecipes.get(position);
    }

    public interface OnRecipeClickedListener {
        void onRecipeClicked(Recipe recipe);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name)
        TextView name;
        @BindView(R.id.recipe_image)
        ImageView image;

        MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            mListener.onRecipeClicked(getData(getAdapterPosition()));
        }
    }
}
