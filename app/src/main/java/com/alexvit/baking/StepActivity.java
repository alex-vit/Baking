package com.alexvit.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.baking.entity.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 8/5/2017.
 */

public class StepActivity extends AppCompatActivity {

    public static final String TAG_PARCEL_STEP_LIST = "TAG_PARCEL_STEP_LIST";
    public static final String TAG_PARCEL_STEP_NUMBER = "TAG_PARCEL_STEP_NUMBER";

    StepFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        mFragment = (StepFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_step);
        mFragment.setSteps(getSteps(), getStepNumber());
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
