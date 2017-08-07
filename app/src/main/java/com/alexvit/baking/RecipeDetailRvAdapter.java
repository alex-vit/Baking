package com.alexvit.baking;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvit.baking.entity.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexander.vitjukov on 02.08.2017.
 */

class RecipeDetailRvAdapter extends RecyclerView.Adapter<RecipeDetailRvAdapter.MyViewHolder> {

    private List<Step> mSteps = null;
    private OnStepClickedListener mListener;

    public RecipeDetailRvAdapter(OnStepClickedListener listener) {
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_step, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Step step = getData(position);
        holder.number.setText(String.valueOf(step.number));
        holder.shortDescription.setText(step.shortDescription);
    }

    @Override
    public int getItemCount() {
        return (mSteps == null) ? 0 : mSteps.size();
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    private Step getData(int position) {
        return mSteps.get(position);
    }

    public interface OnStepClickedListener {
        void onStepClicked(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_number)
        TextView number;
        @BindView(R.id.step_short_description)
        TextView shortDescription;


        MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            mListener.onStepClicked(getAdapterPosition());
        }
    }
}
