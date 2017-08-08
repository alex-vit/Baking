package com.alexvit.baking;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
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

    public static final String TAG_PARCEL_STEPS = "TAG_PARCEL_STEPS";
    public static final String TAG_PARCEL_POSITION = "TAG_PARCEL_POSITION";

    @BindView(R.id.step_fragment_container)
    LinearLayout mStepFragmentContainer;

    StepFragment mStepFragment;

    private List<Step> mSteps;
    private int mPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // call before setContentView
        if (isLandscape()) goFullScreen();

        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            // restore?
        } else {
            mSteps = getSteps();
            mPosition = getPosition();

            mStepFragment = StepFragment.newInstance(mSteps, mPosition);
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(mStepFragmentContainer.getId(), mStepFragment);
            t.commit();
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//    }
//
    private List<Step> getSteps() {
        ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(TAG_PARCEL_STEPS);
        if (steps == null) {
            throw new NullPointerException("Steps extra is null.");
        }
        return steps;
    }

    private int getPosition() {
        return getIntent().getIntExtra(TAG_PARCEL_POSITION, 0);
    }

    private boolean isLandscape() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void goFullScreen() {
        // request feature before getSupportActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
