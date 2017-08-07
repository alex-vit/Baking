package com.alexvit.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alexvit.baking.entity.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment {

    public static final String KEY_ARG_STEP = "KEY_ARG_STEP";
    public static final String KEY_ARG_COUNT = "KEY_ARG_COUNT";

    @BindView(R.id.step_number)
    TextView tvNumber;
    @BindView(R.id.step_short_description)
    TextView tvShortDescription;
    @BindView(R.id.step_description)
    TextView tvDescription;
    @BindView(R.id.prev_step)
    Button mPrevButton;
    @BindView(R.id.next_step)
    Button mNextButton;

    private Step mStep = null;
    private int mCount = 0;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(Step step, int count) {

        Bundle args = buildArgsBundle(step, count);
        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mStep = getStep(getArguments(), savedInstanceState);
        mCount = getCount(getArguments(), savedInstanceState);
        onDataChanged(mStep, mCount);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_ARG_STEP, mStep);
        outState.putInt(KEY_ARG_COUNT, mCount);
    }

    public void onDataChanged(Step step, int count) {

        mStep = step;
        mCount = count;

        boolean hasPrev = (step.number > 0);
        boolean hasNext = (step.number < count - 1);

        tvNumber.setText(String.valueOf(step.number));
        tvShortDescription.setText(step.shortDescription);
        tvDescription.setText(step.description);

        mPrevButton.setVisibility((hasPrev)
                ? View.VISIBLE
                : View.INVISIBLE
        );
        mNextButton.setVisibility((hasNext)
                ? View.VISIBLE
                : View.INVISIBLE
        );
    }

    private static Bundle buildArgsBundle(Step step, int count) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG_STEP, step);
        args.putInt(KEY_ARG_COUNT, count);
        return args;
    }

    private Step getStep(Bundle args, Bundle state) {
        Step step = null;

        Bundle bundle = (state != null) ? state : args;
        if (bundle != null) {
            step = bundle.getParcelable(KEY_ARG_STEP);
        }
        return step;
    }

    private int getCount(Bundle args, Bundle state) {
        int count = 0;

        Bundle bundle = (state != null) ? state : args;
        if (bundle != null) {
            count = bundle.getInt(KEY_ARG_COUNT);
        }
        return count;
    }
}
