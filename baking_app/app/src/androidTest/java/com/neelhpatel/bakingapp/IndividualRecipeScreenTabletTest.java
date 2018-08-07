package com.neelhpatel.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class IndividualRecipeScreenTabletTest {
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<IndividualRecipeActivity> mIndividualRecipeActivityTestRule = new ActivityTestRule<>(IndividualRecipeActivity.class);

    private IdlingResource idlingResource;
    private IdlingRegistry idlingRegistry = IdlingRegistry.getInstance();

    @Before
    public void registerIdlingResource() {
        idlingResource = mMainActivityTestRule.getActivity().getIdlingResource();
        idlingRegistry.register(idlingResource);
    }

    @Test
    public void clickGridViewItem_CheckFragmentPositioningAndDisplays() {
        onView(withId(R.id.recipes_rv)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_rv)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients_container)).check(isCompletelyAbove(withId(R.id.steps_rv)));
        onView(withId(R.id.step_fragment_container)).check(isCompletelyLeftOf(withId(R.id.step_container_container)));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            idlingRegistry.unregister(idlingResource);
        }
    }
}
