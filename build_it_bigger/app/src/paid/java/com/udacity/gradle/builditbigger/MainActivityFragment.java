package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.neelhpatel.androidjokelib.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{

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

    }

    //processFinish gets called from the AsyncResponse interface to start intent 
    private EndpointsAsyncTask createNewAsyncTask() {
        return new EndpointsAsyncTask(new EndpointsAsyncTask.AysncResponse() {
            @Override
            public void processFinish(String joke) {
                mProgressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), JokeActivity.class);
                intent.putExtra(JokeActivity.JOKE_KEY, joke);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jokeButton:
                createNewAsyncTask().execute(getContext());
                mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
