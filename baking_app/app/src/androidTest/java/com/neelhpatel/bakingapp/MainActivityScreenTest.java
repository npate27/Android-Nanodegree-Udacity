package com.neelhpatel.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)

public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;
    private IdlingRegistry idlingRegistry = IdlingRegistry.getInstance();

    @Before
    public void registerIdlingResource() {
        idlingResource = mActivityTestRule.getActivity().getIdlingResource();
        idlingRegistry.register(idlingResource);
    }

    @Test
    public void clickGridViewItem_OpenIngredientsActivityAndNavigateBack() {
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.recipes_rv)).check(matches(isDisplayed()));
    }

    @Test
    public void clickGridViewItem_CheckIntent() {
        Intents.init();
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasExtraWithKey(MainActivity.RECIPE_KEY));
        Intents.release();
    }

    @Test
    public void clickGridViewItem_CheckActionBarTitle() {
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withText("Nutella Pie"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Nutella Pie")));
    }

    public void clickGridViewItem_CheckMenuWidgetOption() {
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.add_to_widget_option), withText("ADD TO WIDGET"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("ADD TO WIDGET")));
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
