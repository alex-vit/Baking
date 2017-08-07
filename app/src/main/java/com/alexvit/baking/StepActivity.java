package com.alexvit.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.alexvit.baking.entity.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandrs Vitjukovs on 8/5/2017.
 */

public class StepActivity extends AppCompatActivity
        implements StepFragment.OnPageNavigationListener {

    public static final String TAG_PARCEL_STEP_LIST = "TAG_PARCEL_STEP_LIST";
    public static final String TAG_PARCEL_STEP_NUMBER = "TAG_PARCEL_STEP_NUMBER";

    @BindView(R.id.step_fragment_container)
    LinearLayout mStepFragmentContainer;

    StepFragment mStepFragment;

    private List<Step> mSteps;
    private int mNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            // restore?
        } else {
            mSteps = getSteps();
            mNumber = getStepNumber();

            mStepFragment = StepFragment.newInstance(mSteps.get(mNumber), mSteps.size());
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(mStepFragmentContainer.getId(), mStepFragment);
            t.commit();
        }
    }

    @Override
    public void onPrev(int position) {
        mNumber = Math.max(0, position - 1);
        Step step = mSteps.get(mNumber);
        mStepFragment.onDataChanged(step, mSteps.size());
    }

    @Override
    public void onNext(int position) {
        mNumber = Math.min(position + 1, mSteps.size() - 1);
        Step step = mSteps.get(mNumber);
        mStepFragment.onDataChanged(step, mSteps.size());
    }

    private List<Step> getSteps() {
        ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(TAG_PARCEL_STEP_LIST);
        if (steps == null) {
            throw new NullPointerException("Steps extra is null.");
        }
        return steps;
    }

    private int getStepNumber() {
        return getIntent().getIntExtra(TAG_PARCEL_STEP_NUMBER, 0);
    }
}
