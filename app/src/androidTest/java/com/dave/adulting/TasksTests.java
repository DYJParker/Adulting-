package com.dave.adulting;

import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import com.dave.adulting.CommonInfrastructure.PagerController;
import com.dave.adulting.Tasks.TasksController;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Dave - Work on 5/25/2017.
 */

public class TasksTests extends PagerTest {
    private static @IdRes int RVID = R.id.tasksRV;


    @Test
    public void tasksAdd(){
        String title = Long.toString(GregorianCalendar.getInstance().getTimeInMillis());

        onView(displayedAnd(withId(PagerController.ADD_MENU_ID)))
                .perform(click());
        Matcher<View> diaEntry = isDescendantOfA(withId(R.id.taskDialog));
        onView(displayedAnd(allOf(withId(R.id.taskDiaDescription), diaEntry)))
                .perform(replaceText(title));
        onView(displayedAnd(withText("OK"))).perform(click());
        SystemClock.sleep(1000);
        onView(displayedAnd(withId(RVID))).
                perform(RecyclerViewActions.scrollTo(firstMatcher(hasDescendant(withText(title)))));
    }

    @Test
    public void taskRemove(){
        clickRvButtonThenCheckRemoved(RVID,R.id.check);
    }

    @Override
    String getKey() {
        return TasksController.KEY;
    }

    @Before
    @Override
    public void check() {
        tabCheck(R.id.twoLine, RVID);
    }
}
