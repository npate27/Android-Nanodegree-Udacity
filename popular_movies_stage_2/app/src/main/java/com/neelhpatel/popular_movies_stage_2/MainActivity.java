package com.neelhpatel.popular_movies_stage_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.neelhpatel.popular_movies_stage_2.adapters.MovieInfoAdapter;
import com.neelhpatel.popular_movies_stage_2.database.AppDatabase;
import com.neelhpatel.popular_movies_stage_2.model.MainViewModel;
import com.neelhpatel.popular_movies_stage_2.model.MovieInfo;
import com.neelhpatel.popular_movies_stage_2.utils.MovieDBJsonUtils;
import com.neelhpatel.popular_movies_stage_2.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements MovieInfoAdapter.MoviesOnClickHandler {

    private static int currentSortPreference;
    private static MovieInfoAdapter mMovieInfoAdapter;
    private static final String SORT_KEY = "sort_key"; //Key for sort preference
    public static final String MOVIEINFO_KEY = "movieInfo_key"; //Key for passing object via intent
    private static List<MovieInfo> mMainMovieInfos = new ArrayList<>();

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getsInstance(getApplicationContext());
        currentSortPreference = readSortPreference();
        RecyclerView recyclerView = findViewById(R.id.poster_images_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fetchMoviePosters();
        mMovieInfoAdapter = new MovieInfoAdapter(this, mMainMovieInfos, this);
        recyclerView.setAdapter(mMovieInfoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSortDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(MovieInfo movieInfo) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MOVIEINFO_KEY, movieInfo);
        startActivity(intent);
    }

    /**
     * Starts AsyncTask which will fetch movie data in the background depending
     * on user's selected sort preference. If no internet, it defaults to favorites
     */
    private void fetchMoviePosters() {
        if(currentSortPreference != 2) {
            if (NetworkUtils.isConnectedToInternet(this)) {
                URL mainMovieUrl = NetworkUtils.getMainUrl(currentSortPreference);
                new MainMovieTask().execute(mainMovieUrl);
            } else {
                Toast.makeText(this, getResources().getString(R.string.toast_internet_string),
                    Toast.LENGTH_SHORT).show();
                currentSortPreference = 2;
            }
        }
        else {
            MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            viewModel.getFavorites().observe(this, new Observer<List<MovieInfo>>() {
                 @Override
                 public void onChanged(@Nullable List<MovieInfo> movieInfos) {
                     mMainMovieInfos = movieInfos;
                     mMovieInfoAdapter.changeData(mMainMovieInfos);
                 }
             });
        }
    }

    /**
     * Gets user's sort preference, if present
     *
     * @return user's preferred sort order or the default order
     */
    private int readSortPreference() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(SORT_KEY, 0);
    }

    /**
     * Writes user's current sort preference to be read upon opening the app again.
     */
    private void writeSortPreference() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(SORT_KEY, currentSortPreference);
        editor.apply();
    }

    /**
     * Opens an AlertDialog that features two options (Popular or Top-Rated) in a single-choice
     * option list. Upon choosing a sort order, the choice is written as a preference,
     * the new movie data is fetched and the dialog closes.
     */
    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                currentSortPreference = which;
                fetchMoviePosters();
                dialog.dismiss();
                writeSortPreference();
            }
        };

        builder.setTitle(R.string.dialog_sort_string)
            .setCancelable(true)
            .setNegativeButton(R.string.cancel, dialogListener)
            .setSingleChoiceItems(R.array.sort_options_array, currentSortPreference, dialogListener);
        builder.show();
    }

    static class MainMovieTask extends AsyncTask<URL, Void, List<MovieInfo>> {
        @Override
        protected List<MovieInfo> doInBackground(URL... urls) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            URL url = urls[0];
            List<MovieInfo> movieInfos = new ArrayList<>();

            try {
                String jsonMainResponse = NetworkUtils.getResponseFromHttpUrl(url);
                movieInfos = MovieDBJsonUtils.getMoviesFromJson(jsonMainResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return movieInfos;
        }

        @Override
        protected void onPostExecute(List<MovieInfo> movieInfos) {
            mMainMovieInfos = movieInfos;
            mMovieInfoAdapter.changeData(mMainMovieInfos);
            super.onPostExecute(movieInfos);
        }
    }
}
