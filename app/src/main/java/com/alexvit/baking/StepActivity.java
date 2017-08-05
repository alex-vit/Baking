package com.alexvit.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvit.baking.entity.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alexvit.baking.StepActivity.StepFragment.KEY_ARG_STEP;

/**
 * Created by Aleksandrs Vitjukovs on 8/5/2017.
 */

public class StepActivity extends AppCompatActivity {

    public static final String TAG_PARCEL_STEP_LIST = "TAG_PARCEL_STEP_LIST";
    public static final String TAG_PARCEL_STEP_NUMBER = "TAG_PARCEL_STEP_NUMBER";

    StepsPagerAdapter mPagerAdapter;
    ViewPager mViewPager;

    private List<Step> mSteps;
    private int mNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        mSteps = getSteps();
        mNumber = getStepNumber();
        initPager();
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

    private void initPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager);

        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                32 * 2,
                getResources().getDisplayMetrics());
        mViewPager.setPageMargin(-margin);
        mViewPager.setOffscreenPageLimit(2);

        mPagerAdapter = new StepsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mNumber);
    }

    public static class StepFragment extends Fragment {

        public static final String KEY_ARG_STEP = "KEY_ARG_STEP";

        @BindView(R.id.step_number)
        TextView tvNumber;
        @BindView(R.id.step_short_description)
        TextView tvShortDescription;
        @BindView(R.id.step_description)
        TextView tvDescription;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            Step step = (Step) getArguments().get(KEY_ARG_STEP);
            if (step == null) throw new NullPointerException("No step passed in arguments.");

            View rootView = inflater.inflate(R.layout.fragment_step, container, false);
            ButterKnife.bind(this, rootView);

            tvNumber.setText(String.valueOf(step.number));
            tvShortDescription.setText(step.shortDescription);
            tvDescription.setText(step.description);

            return rootView;
        }
    }

    private class StepsPagerAdapter extends FragmentPagerAdapter {

        StepsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new StepFragment();

            Bundle args = new Bundle();
            args.putParcelable(KEY_ARG_STEP, mSteps.get(position));

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return (mSteps == null) ? 0 : mSteps.size();
        }
    }
}
