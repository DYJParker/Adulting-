package com.dave.adulting.CommonInfrastructure;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.support.RouterPagerAdapter;
import com.dave.adulting.Perishables.PerishableController;
import com.dave.adulting.R;

/**
 * Created by Dave - Work on 5/23/2017.
 */

public class PagerController extends Controller {
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private final RouterPagerAdapter mPager;
    private static final String TAG = "PagerController";
    private static final Class[] CONTROLLERS = new Class[]{
            PerishableController.class
    };

    public PagerController() {
        //ViewGroup container = (ViewGroup) findViewById(R.id.conductorFrame);

        mPager = new RouterPagerAdapter(this) {
            @Override
            public void configureRouter(@NonNull Router router, int position) {
                if (!router.hasRootController()) {
                    try {
                        FireBaseController page = (FireBaseController)CONTROLLERS[position].newInstance();
                        Log.d(TAG, "configureRouter: " + page);
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
                Log.d(TAG, "getPageTitle: ");
                try {
                    return (String)CONTROLLERS[position].getField("KEY").get(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.controller_pager,container,false);
        mTabs = (TabLayout) view.findViewById(R.id.conductorTabs);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        return view;
    }



    @Override
    protected void onAttach(@NonNull View view) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(view);
        mViewPager.setAdapter(mPager);
        mTabs.setupWithViewPager(mViewPager, false);
    }


}
