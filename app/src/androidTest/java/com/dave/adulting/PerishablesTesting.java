package com.dave.adulting;

import android.app.Dialog;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dave.adulting.CommonInfrastructure.CommonObject;
import com.dave.adulting.CommonInfrastructure.ConductorActivity;
import com.dave.adulting.CommonInfrastructure.FireBaseController;
import com.dave.adulting.CommonInfrastructure.PagerController;
import com.dave.adulting.Perishables.PerishableController;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.typeCompatibleWith;

/**
 * Created by Dave - Work on 5/25/2017.
 */

public class PerishablesTesting extends PagerTest {
    private static @IdRes int ID = R.id.perishableRV;

    @Test
    public void groceryAdd() {
        Random rand = new Random();

        String title = Long.toString(GregorianCalendar.getInstance().getTimeInMillis());
        String duration = Integer.toString(rand.nextInt(50));

        DateFormat df = DateFormat.getDateInstance(CommonObject.DATE_FORMAT);

        onView(displayedAnd(withId(PagerController.ADD_MENU_ID)))
                .perform(click());
        Matcher<View> diaEntry = isDescendantOfA(withId(R.id.perDialog));
        onView(displayedAnd(allOf(withId(R.id.perDiaDescription), diaEntry)))
                .perform(replaceText(title));
        onView(displayedAnd(allOf(withId(R.id.perDiaEstimate), diaEntry)))
                .perform(replaceText(duration));
        onView(displayedAnd(withText("OK"))).perform(click());
        SystemClock.sleep(1000);
        onView(displayedAnd(withId(ID))).
                perform(RecyclerViewActions.scrollTo(firstMatcher(hasDescendant(withText(title)))));
        Calendar cal = GregorianCalendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,Integer.parseInt(duration));
        String deadline = df.format(cal.getTime());
        onView(displayedAnd(withText(title))).check(matches(hasSibling(displayedAnd(withText(deadline)))));
    }

    @Test
    public void groceryMoveToShopping(){
        clickRvButtonThenCheckRemoved(ID,R.id.check);
    }

    @Override
    String getKey() {
        return PerishableController.KEY;
    }

    @Before
    @Override
    public void check() {
        tabCheck(R.id.threeLine, ID);
    }
}
