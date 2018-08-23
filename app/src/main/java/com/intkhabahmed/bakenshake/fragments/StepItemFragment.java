package com.intkhabahmed.bakenshake.fragments;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.intkhabahmed.bakenshake.R;
import com.intkhabahmed.bakenshake.databinding.StepItemBinding;
import com.intkhabahmed.bakenshake.models.Step;

public class StepItemFragment extends Fragment {

    private StepItemBinding mStepItemBinding;
    private SimpleExoPlayer player;
    private Step mStep;

    public StepItemFragment() {
    }

    public void setStep(Step mStep) {
        this.mStep = mStep;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(getString(R.string.steps));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStepItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
                R.layout.step_item, container, false);
        return mStepItemBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mStep != null) {
            mStepItemBinding.descriptionTv.setText(mStep.getDescription());
            mStepItemBinding.shortDescriptionTv.setText(mStep.getShortDescription());
            if (!TextUtils.isEmpty(mStep.getThumbnailUrl())) {
                Glide.with(getActivity()).asBitmap().load(Uri.parse(mStep.getThumbnailUrl())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mStepItemBinding.playerView.setDefaultArtwork(resource);
                    }
                });
                ((ImageView) view.findViewById(R.id.exo_artwork)).setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            if (!TextUtils.isEmpty(mStep.getVideoUrl())) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mStepItemBinding.descriptionContainer.setVisibility(View.GONE);
                }
                initializePlayer();
                mStepItemBinding.playerView.setPlayer(player);
                if (savedInstanceState != null && savedInstanceState.getLong(getString(R.string.player_current_position)) != 0) {
                    player.seekTo(savedInstanceState.getLong(getString(R.string.player_current_position)));
                    player.setPlayWhenReady(savedInstanceState.getBoolean(getString(R.string.player_state)));
                }
            } else {
                mStepItemBinding.playerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null && player.getCurrentPosition() != 0) {
            outState.putLong(getString(R.string.player_current_position), player.getCurrentPosition());
            outState.putBoolean(getString(R.string.player_state), player.getPlayWhenReady());
        }
        outState.putParcelable(getString(R.string.steps), mStep);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        player = null;
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    private void initializePlayer() {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), getString(R.string.app_name)), bandwidthMeter);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mStep.getVideoUrl()));
        player.prepare(mediaSource);
        player.setPlayWhenReady(false);
    }
}
