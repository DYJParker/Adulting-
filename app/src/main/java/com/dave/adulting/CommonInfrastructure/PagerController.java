package com.dave.adulting.CommonInfrastructure;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.support.RouterPagerAdapter;
import com.dave.adulting.Perishables.PerishableController;
import com.dave.adulting.R;
import com.dave.adulting.Tasks.TasksController;
import com.dave.adulting.ToBuy.ToBuyController;

import java.util.List;

/**
 * Created by Dave - Work on 5/23/2017.
 */

public class PagerController extends Controller {
    private static final int ADD_MENU_ID = 99;
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private final RouterPagerAdapter mPager;
    private static final String TAG = "PagerController";
    private static final Class[] CONTROLLERS = new Class[]{
            PerishableController.class,
            ToBuyController.class,
            TasksController.class
    };

    public PagerController() {
        //ViewGroup container = (ViewGroup) findViewById(R.id.conductorFrame);

        mPager = new RouterPagerAdapter(this) {
            @Override
            public void configureRouter(@NonNull Router router, int position) {
                if (!router.hasRootController()) {
                    try {
                        FireBaseController page = (FireBaseController)CONTROLLERS[position].newInstance();
                        router.setRoot(RouterTransaction.with(page));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public int getCount() {
                return CONTROLLERS.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String ret = null;
                try {
                    ret = (String) CONTROLLERS[position].getField("KEY").get(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ret;
            }
        };
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.controller_pager,container,false);
        mTabs = (TabLayout) getActivity().findViewById(R.id.conductorTabs);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        setHasOptionsMenu(true);
        return view;
    }



    @Override
    protected void onAttach(@NonNull View view) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(view);
        mViewPager.setAdapter(mPager);
        mTabs.setupWithViewPager(mViewPager, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem mi = menu.add(Menu.NONE,ADD_MENU_ID,99,"Add New Item");
        mi.setIcon(R.drawable.ic_add_box_24dp);
        mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == ADD_MENU_ID){
            List<RouterTransaction> stack = mPager.getRouter(mViewPager.getCurrentItem()).getBackstack();
            ((FireBaseController)stack.get(stack.size()-1).controller()).adder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
