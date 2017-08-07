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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepFragment extends Fragment {

    public static final String KEY_ARG_STEPS = "KEY_ARG_STEPS";
    public static final String KEY_ARG_POSITION = "KEY_ARG_POSITION";

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

    private List<Step> mSteps = null;
    private int mPosition = 0;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(List<Step> steps) {
        return newInstance(steps, 0);
    }

    public static StepFragment newInstance(List<Step> steps, int position) {

        Bundle args = buildArgsBundle(steps, position);
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

        mSteps = getSteps(getArguments(), savedInstanceState);
        mPosition = getPosition(getArguments(), savedInstanceState);

        setPosition(mPosition);
        updateUi();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(KEY_ARG_STEPS, new ArrayList<>(mSteps));
        outState.putInt(KEY_ARG_POSITION, mPosition);
    }

    public void setPosition(int position) {

        mPosition = position;

        boolean hasPrev = (position > 0);
        boolean hasNext = (position < mSteps.size() - 1);

        Step step = mSteps.get(position);

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

    private void updateUi() {

        boolean hasPrev = (mPosition > 0);
        boolean hasNext = (mPosition < mSteps.size() - 1);

        Step step = mSteps.get(mPosition);

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

    private static Bundle buildArgsBundle(List<Step> steps, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_ARG_STEPS, new ArrayList<>(steps));
        args.putInt(KEY_ARG_POSITION, position);
        return args;
    }

    private List<Step> getSteps(Bundle args, Bundle state) {
        List<Step> steps = null;

        Bundle bundle = (state != null) ? state : args;
        if (bundle != null) {
            steps = bundle.getParcelableArrayList(KEY_ARG_STEPS);
        }
        return steps;
    }

    private int getPosition(Bundle args, Bundle state) {
        int count = 0;

        Bundle bundle = (state != null) ? state : args;
        if (bundle != null) {
            count = bundle.getInt(KEY_ARG_POSITION);
        }
        return count;
    }

    @OnClick(R.id.prev_step)
    void onPrev() {

    }

    @OnClick(R.id.next_step)
    void onNext() {

    }
}
