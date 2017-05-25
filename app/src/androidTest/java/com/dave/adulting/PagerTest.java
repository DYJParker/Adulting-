package com.dave.adulting;

import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.dave.adulting.CommonInfrastructure.ConductorActivity;
import com.dave.adulting.Perishables.PerishableController;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Dave - Work on 5/25/2017.
 */

public abstract class PagerTest {
    abstract String getKey();

    abstract public void check();

    @Rule
    public ActivityTestRule<ConductorActivity> mActivityTestRule = new ActivityTestRule<>(ConductorActivity.class);

    protected Matcher<View> displayedAnd(Matcher<? super View> criterion) {
        return allOf(isDisplayed(), criterion);
    }

    protected void tabCheck(@IdRes int positive, @IdRes int RV) {
        SystemClock.sleep(1000);
        onView(displayedAnd(allOf(isDescendantOfA(withId(R.id.conductorTabs)), withText(getKey()))))
                .perform(click());
        SystemClock.sleep(1000);
        onView(displayedAnd(withId(RV)))
                .check(matches(hasDescendant(displayedAnd(withId(positive)))));
        onView(childAtPosition(displayedAnd(withId(RV)),4))
                .check(matches(hasDescendant(withId(positive))));

        //assertThat("RV has too few children",
        //        ((RecyclerView) mActivityTestRule.getActivity().findViewById(RV)).getChildCount(),
        //        greaterThanOrEqualTo(5));
    }

    protected static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("child at position " + position + " in parent ");
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

    protected void clickRvButtonThenCheckRemoved(@IdRes int RV, @IdRes int thingToClick) {
        RecyclerView rv = ((RecyclerView) mActivityTestRule.getActivity().findViewById(RV));

        int childIndex = (new Random()).nextInt(rv.getChildCount());

        View child = rv.getChildAt(childIndex);

        RecyclerView.ViewHolder vh = rv.getChildViewHolder(child);

        onView(displayedAnd(withId(RV))).perform(RecyclerViewActions.scrollToPosition(childIndex));
        onView(displayedAnd(allOf(
                isDescendantOfA(childAtPosition(displayedAnd(withId(RV)), childIndex)),
                withId(thingToClick))
        )).perform(click()).check(matchesVH(vh));
    }

    public static ViewAssertion matchesVH(final RecyclerView.ViewHolder VH) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noView) {
                if (view != null) {
                    RecyclerView parentRV = null;
                    View maybeRVChild = view;
                    ViewParent parentChecker = view.getParent();
                    while (parentChecker instanceof View) {
                        if (parentChecker instanceof RecyclerView) {
                            parentRV = (RecyclerView) parentChecker;
                            break;
                        }
                        maybeRVChild = (View) parentChecker;
                        parentChecker = parentChecker.getParent();
                    }
                    if (parentRV == null) {
                        throw new IllegalArgumentException("View must be child of a RecyclerView: "
                                + HumanReadables.describe(view));
                    }
                    if (parentRV.getChildViewHolder(maybeRVChild) == VH) {
                        assertThat("View is still original view: "
                                + HumanReadables.describe(view), false, is(true));
                    }
                }
            }
        };
    }

    //Modified from https://stackoverflow.com/a/39756832
    public static Matcher<View> firstMatcher(final Matcher<View> matcher) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            protected boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("the first view: ");
                matcher.describeTo(description);
            }
        };
    }
}
