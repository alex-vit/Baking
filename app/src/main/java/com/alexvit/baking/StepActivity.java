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

public class StepActivity extends AppCompatActivity {

    public static final String TAG_PARCEL_STEP_LIST = "TAG_PARCEL_STEP_LIST";
    public static final String TAG_PARCEL_STEP_NUMBER = "TAG_PARCEL_STEP_NUMBER";

    @BindView(R.id.step_fragment_container)
    LinearLayout mFragmentContainer;

    private List<Step> mSteps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        mSteps = getSteps();
        int position = getStepNumber();

        StepFragment fragment = configureFragment(mSteps, position);
        displayFragment(getSupportFragmentManager(), mFragmentContainer.getId(), fragment);
    }

    static StepFragment configureFragment(List<Step> steps, int position) {
        int total = steps.size();
        boolean hasNext = (position < total - 1);
        boolean hasPrev = (position > 0);
        Step step = steps.get(position);

        return StepFragment.newInstance(step, position, hasNext, hasPrev);
    }

    static void displayFragment(FragmentManager fm, int container, Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container, fragment);
        transaction.commit();
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
