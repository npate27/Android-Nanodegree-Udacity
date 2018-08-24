package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.neelhpatel.androidjokelib.JokeActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    public static InterstitialAd mInterstitialAd = null;
    private Intent mIntent = null;
    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.jokeButton).setOnClickListener(this);
        mProgressBar = view.findViewById(R.id.progressBar);

        //Initialize and load Ad, start Joke activity after Ad is closed
        mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
            mInterstitialAd.loadAd(new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build());
            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    mProgressBar.setVisibility(View.GONE);
                    startActivity(mIntent);
                }
            });
    }

    //processFinish gets called from the AsyncResponse interface to set the intent up
    private EndpointsAsyncTask createNewAsyncTask() {
        return new EndpointsAsyncTask(new EndpointsAsyncTask.AysncResponse() {
            @Override
            public void processFinish(String joke) {
                Intent intent = new Intent(getContext(), JokeActivity.class);
                intent.putExtra(JokeActivity.JOKE_KEY, joke);
                mIntent = intent;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jokeButton:
                if(mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    createNewAsyncTask().execute(getContext());
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
        }
    }
}
