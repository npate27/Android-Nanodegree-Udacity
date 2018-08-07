package com.neelhpatel.bakingapp.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.model.StepInfo;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class StepFragment extends Fragment implements View.OnClickListener {
    private static final String STEP_KEY = "step_key";
    private static final String STEPS_KEY = "steps_key";
    private static final String TWOPANE_KEY = "twopane_key";
    private static final String PLAYBACK_POS_KEY = "playback_pos_key";

    private boolean mTwoPane;
    private long mPlayBackPosition;
    private StepInfo mStepInfo;
    private SimpleExoPlayer mExoPlayer;
    private ArrayList<StepInfo> mStepsInfos;

    @BindView(R.id.next_btn) Button mNextBtn;
    @BindView(R.id.previous_btn) Button mPreviousBtn;
    @BindView(R.id.playerView) PlayerView mPlayerView;
    @BindView(R.id.step_instruction_tv) TextView mInstructionStep;

    public StepFragment() {}

    public static StepFragment newInstance(StepInfo stepInfo, ArrayList<StepInfo> stepsInfos, boolean twoPane){
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_KEY, stepInfo);
        bundle.putParcelableArrayList(STEPS_KEY, stepsInfos);
        bundle.putBoolean(TWOPANE_KEY, twoPane);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_cooking, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Init vars based on arguments or savedInstanceState.
        if(savedInstanceState != null) {
            mTwoPane = savedInstanceState.getBoolean(TWOPANE_KEY);
            mStepInfo = savedInstanceState.getParcelable(STEP_KEY);
            mStepsInfos = savedInstanceState.getParcelableArrayList(STEPS_KEY);
            mPlayBackPosition = savedInstanceState.getLong(PLAYBACK_POS_KEY);
        } else {
            mTwoPane = Objects.requireNonNull(getArguments()).getBoolean(TWOPANE_KEY);
            mStepInfo = getArguments().getParcelable(STEP_KEY);
            mStepsInfos = getArguments().getParcelableArrayList(STEPS_KEY);
            mPlayBackPosition = 0;
        }

        mInstructionStep.setText(mStepInfo.getDescription());
        initButtons();

        //Hide player if not valid url, otherwise init player at position it was last at
        if (mStepInfo.getVideoURL().equals("")) {
            mPlayerView.setVisibility(GONE);
        } else {
            initializePlayer();
            mExoPlayer.seekTo(mPlayBackPosition);
        }

        //For making video play fullscreen if in landscape for normal sized phones
        int currentOrientation = Objects.requireNonNull(getContext()).getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE && !mTwoPane && mPlayerView.getVisibility() != GONE) {
            mPreviousBtn.setVisibility(GONE);
            mNextBtn.setVisibility(GONE);
            setImmersiveMode();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(TWOPANE_KEY, mTwoPane);
        outState.putParcelable(STEP_KEY, mStepInfo);
        outState.putParcelableArrayList(STEPS_KEY, mStepsInfos);
        outState.putLong(PLAYBACK_POS_KEY, mPlayBackPosition);
    }

    private void initializePlayer() {
        if(mExoPlayer == null) {
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            TrackSelector trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            Uri uri = Uri.parse(mStepInfo.getVideoURL());
            MediaSource mediaSource = buildMediaSource(uri);
            mExoPlayer.prepare(mediaSource, true, false);
            mExoPlayer.setPlayWhenReady(true);
            mPlayerView.setVisibility(View.VISIBLE);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-baking")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mPlayBackPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


    @Override
    public void onClick(View v) {
        int currentIndex = mStepsInfos.indexOf(mStepInfo);
        int containerId = mTwoPane ? R.id.step_content_container : R.id.step_fragment_container;

        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.previous_btn:
                fragment = StepFragment.newInstance(mStepsInfos.get(--currentIndex), mStepsInfos, mTwoPane);
                break;
            case R.id.next_btn:
                fragment = StepFragment.newInstance(mStepsInfos.get(++currentIndex), mStepsInfos, mTwoPane);
                break;
        }

        if(fragment != null) {
            ft.replace(containerId, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void initButtons() {
        if(mStepsInfos.indexOf(mStepInfo) == 0){
            mPreviousBtn.setVisibility(GONE);
        } else {
            mPreviousBtn.setOnClickListener(this);
        }

        if(mStepsInfos.indexOf(mStepInfo) == mStepsInfos.size()-1){
            mNextBtn.setVisibility(GONE);
        } else {
            mNextBtn.setOnClickListener(this);
        }
    }

    private void setImmersiveMode() {
        View decorView = Objects.requireNonNull(getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}