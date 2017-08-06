package com.alexvit.baking;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvit.baking.entity.Step;
import com.alexvit.baking.loader.BitmapAsyncTaskLoader;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandrs Vitjukovs on 8/6/2017.
 */
public class StepItemFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Bitmap> {

    public static final String KEY_ARG_STEP = "KEY_ARG_STEP";
    private static final String LOADER_ARG_URL = "LOADER_ARG_URL";
    @BindView(R.id.step_number)
    TextView tvNumber;
    @BindView(R.id.step_short_description)
    TextView tvShortDescription;
    @BindView(R.id.step_video)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.step_description)
    TextView tvDescription;
    private int LOADER_ID;
    private Step mStep;
    private SimpleExoPlayer mPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Step step = (Step) getArguments().get(KEY_ARG_STEP);
        if (step == null) throw new NullPointerException("No step passed in arguments.");
        mStep = step;

        View rootView = inflater.inflate(R.layout.fragment_step_item, container, false);
        ButterKnife.bind(this, rootView);

        tvNumber.setText(String.valueOf(step.number));
        tvShortDescription.setText(step.shortDescription);
        tvDescription.setText(step.description);

        LOADER_ID = 100 + step.number;

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadBitmap(mStep.thumbnailURL);
        initPlayer(mStep.videoURL);
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        String url = args.getString(LOADER_ARG_URL);
        return new BitmapAsyncTaskLoader(getContext(), url);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
        mPlayerView.setDefaultArtwork(bitmap);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }

    public void pauseVideo() {
    }

    private void loadBitmap(String url) {

        if (url == null || url.isEmpty()) return;

        Bundle args = new Bundle();
        args.putString(LOADER_ARG_URL, url);
        getLoaderManager().initLoader(LOADER_ID, args, this);
    }

    private void initPlayer(String urlString) {

        if (mPlayer != null) return;

        Uri uri = Uri.parse(urlString);
        TrackSelector trackSelector = new DefaultTrackSelector();
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        String userAgent = this.getClass().getSimpleName() + "-- Baking app";
    }
}
