package com.alexvit.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexvit.baking.entity.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alexvit.baking.StepItemFragment.KEY_ARG_STEP;


public class StepFragment extends Fragment {

    @BindView(R.id.pager)
    ViewPager mViewPager;

    StepsPagerAdapter mPagerAdapter;
    private SimpleExoPlayer mPlayer;


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

        ButterKnife.bind(this, view);
        initPager();
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
        mPagerAdapter.notifyDataSetChanged();
    }

    public void setPosition(int position) {
        mViewPager.setCurrentItem(position);
    }

    public SimpleExoPlayer getPlayer() {
        if (mPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        }

        mPlayer.stop();
        return mPlayer;
    }

    private void initPager() {
        int margin = calculateMargin();
        mViewPager.setPageMargin(-margin);
        mViewPager.setOffscreenPageLimit(2);

        mPagerAdapter = new StepsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            private int mLastPage = mViewPager.getCurrentItem();

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                ((StepItemFragment) mPagerAdapter.getItem(mLastPage)).onPrevious();
                mLastPage = position;
            }
        });
    }

    private int calculateMargin() {
        int pagePaddingDp = (int) (getResources().getDimension(R.dimen.padding_pager_item)
                / getResources().getDisplayMetrics().density);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pagePaddingDp * 2,
                getResources().getDisplayMetrics());
    }

    private class StepsPagerAdapter extends FragmentStatePagerAdapter {

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
