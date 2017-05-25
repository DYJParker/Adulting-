package com.dave.adulting;

import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import com.dave.adulting.CommonInfrastructure.PagerController;
import com.dave.adulting.ToBuy.ToBuyController;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

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

public class ShoppingTest extends PagerTest {
    private static @IdRes int ID = R.id.shoppingRV;

    @Test
    public void shoppingAdd(){
        String title = Long.toString(GregorianCalendar.getInstance().getTimeInMillis());

        shoppingAddHelper(title);

        onView(displayedAnd(withId(ID))).
                perform(RecyclerViewActions.scrollTo(firstMatcher(hasDescendant(withText(title)))));
    }

    private void shoppingAddHelper(String title){
        onView(displayedAnd(withId(PagerController.ADD_MENU_ID)))
                .perform(click());
        onView(displayedAnd(allOf(withId(R.id.shopDiaDescription))))
                .perform(replaceText(title));
        onView(displayedAnd(withText("OK"))).perform(click());
        SystemClock.sleep(1000);
    }

  /*  @Test
    public void shoppingReinit(){
        String title = Long.toString(GregorianCalendar.getInstance().getTimeInMillis());

        shoppingAddHelper(title);


    }*/

    @Test
    public void shoppingRemove(){
        clickRvButtonThenCheckRemoved(ID,R.id.cartRemove);
    }

    @Override
    String getKey() {
        return ToBuyController.KEY;
    }

    @Before
    @Override
    public void check() {
        tabCheck(R.id.shopping,ID);
    }
}
