package com.alexvit.baking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvit.baking.entity.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment {

    public static final String KEY_ARG_STEP = "KEY_ARG_STEP";
    public static final String KEY_ARG_POSITION = "KEY_ARG_POSITION";
    public static final String KEY_ARG_HAS_NEXT = "KEY_ARG_HAS_NEXT";
    public static final String KEY_ARG_HAS_PREV = "KEY_ARG_HAS_PREV";

    @BindView(R.id.step_number)
    TextView tvNumber;
    @BindView(R.id.step_short_description)
    TextView tvShortDescription;
    @BindView(R.id.step_description)
    TextView tvDescription;

    /* TODO
     * 1. add buttons for prev and next
     * 2. add interface for activities to implement (onNext, onPrev)
     * 3. onAttach - cast and save activity into an mListener
     */

    private Step mStep;
    private int mPosition;
    private boolean mHasNext;
    private boolean mHasPrev;

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mStep = (Step) getArguments().get(KEY_ARG_STEP);
        if (mStep == null) throw new NullPointerException("No step passed in arguments. Use newInstance.");
        mPosition = getArguments().getInt(KEY_ARG_POSITION, 0);
        mHasNext = getArguments().getBoolean(KEY_ARG_HAS_NEXT);
        mHasPrev = getArguments().getBoolean(KEY_ARG_HAS_PREV);

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        tvNumber.setText(String.valueOf(mStep.number));
        tvShortDescription.setText(mStep.shortDescription);
        tvDescription.setText(mStep.description);

        return rootView;
    }

    public static StepFragment newInstance(Step step, int position, boolean hasNext, boolean hasPrev) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG_STEP, step);
        args.putInt(KEY_ARG_POSITION, position);
        args.putBoolean(KEY_ARG_HAS_NEXT, hasNext);
        args.putBoolean(KEY_ARG_HAS_PREV, hasPrev);

        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
