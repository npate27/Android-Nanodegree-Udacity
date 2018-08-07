package com.neelhpatel.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class IndividualRecipeScreenTest {

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
    public void clickGridViewItem_CheckMasterListPositioningAndDisplays() {
        onView(withId(R.id.recipes_rv)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_rv)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients_container)).check(isCompletelyAbove(withId(R.id.steps_rv)));
    }

    @Test
    public void clickBeginningAndEndSteps_CheckButtonVisibilitiesAndFragmentTransactions() {
        onView(withId(R.id.recipes_rv)).perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.steps_rv),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.previous_btn)).check(matches(not(isDisplayed())));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.step_fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(456);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.previous_btn), withText("Previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.step_fragment_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(270);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.steps_rv),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                1)));
        recyclerView3.perform(actionOnItemAtPosition(6, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.previous_btn), withText("Previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.step_fragment_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.step_fragment_container),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        onView(withId(R.id.next_btn)).check(matches(not(isDisplayed())));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
    }


    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            idlingRegistry.unregister(idlingResource);
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
