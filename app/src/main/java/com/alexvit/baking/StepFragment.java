package com.alexvit.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvit.baking.entity.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alexvit.baking.StepFragment.StepItemFragment.KEY_ARG_STEP;


public class StepFragment extends Fragment {

    StepsPagerAdapter mPagerAdapter;
    ViewPager mViewPager;

    private List<Step> mSteps;

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_step, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPager();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
        mPagerAdapter.notifyDataSetChanged();
    }

    public void setPosition(int position) {
        mViewPager.setCurrentItem(position);
    }

    private void initPager() {
        mViewPager = (ViewPager) getView().findViewById(R.id.pager);

        int margin = calculateMargin();
        mViewPager.setPageMargin(-margin);
        mViewPager.setOffscreenPageLimit(2);

        mPagerAdapter = new StepsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    private int calculateMargin() {
        int pagePaddingDp = (int) (getResources().getDimension(R.dimen.padding_pager_item)
                / getResources().getDisplayMetrics().density);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pagePaddingDp * 2,
                getResources().getDisplayMetrics());
    }

    public static class StepItemFragment extends Fragment {

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

            View rootView = inflater.inflate(R.layout.fragment_step_item, container, false);
            ButterKnife.bind(this, rootView);

            tvNumber.setText(String.valueOf(step.number));
            tvShortDescription.setText(step.shortDescription);
            tvDescription.setText(step.description);

            return rootView;
        }
    }

    class StepsPagerAdapter extends FragmentPagerAdapter {

        StepsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new StepItemFragment();

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
