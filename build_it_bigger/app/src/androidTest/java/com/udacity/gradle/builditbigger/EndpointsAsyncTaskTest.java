package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void getJoke() throws InterruptedException {
        EndpointsAsyncTask endpointsAsyncTask = createNewAsyncTask();
        endpointsAsyncTask.execute(mActivityRule.getActivity().getApplicationContext());
    }

    private EndpointsAsyncTask createNewAsyncTask() {
        return new EndpointsAsyncTask(new EndpointsAsyncTask.AysncResponse() {
            @Override
            public void processFinish(String joke) {
                assertTrue("Joke is received", joke != null);
            }
        });
    }
}
