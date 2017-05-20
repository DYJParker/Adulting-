package com.dave.adulting.CommonInfrastructure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dave.adulting.Perishables.PerishableActivity;
import com.dave.adulting.R;
import com.dave.adulting.Tasks.TasksActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

/**
 * Created by Dave - Work on 5/16/2017.
 */

public abstract class InfrastructureBaseActivity extends AppCompatActivity {
    protected FirebaseAuth mAuth;
    protected static final int FB_SIGN_IN = 1000;
    protected Intent mFBSignin;
    private static final String TAG = "InfrastructureBaseActiv";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            mFBSignin = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                    .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                    .build();
            startActivityForResult(mFBSignin, FB_SIGN_IN);
        } else {
            onSpecificCreate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FB_SIGN_IN) {
            if (resultCode == ResultCodes.OK){
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                onSpecificCreate();
            }
            else signInError(IdpResponse.fromResultIntent(data));
        }
    }

    protected void signInError(IdpResponse response) {
        String error;
        if (response == null) error = "You pressed the back button";
        else if (response.getErrorCode() == ErrorCodes.NO_NETWORK)
            error = "You don't currently have internet access to sign in";
        else error = "An unknown error occurred";
        Toast.makeText(this, error + ", please try signing in again to access app functionality.", Toast.LENGTH_LONG).show();
        startActivityForResult(mFBSignin, FB_SIGN_IN);
    }

    abstract protected void onSpecificCreate();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        Log.d(TAG, "onCreateOptionsMenu: " + menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.dave.adulting.R.menu.menu_common, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_signout) {
            final AppCompatActivity ctx = this;
            AuthUI.getInstance()
                    .signOut(ctx)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(ctx, ctx.getClass()));
                            finish();
                        }
                    });
            return true;
        }
        else if (id == R.id.actionPerishable){
            startActivity(new Intent(this,PerishableActivity.class));
            return true;
        }
        else if (id == R.id.actionTasks){
            startActivity(new Intent(this,TasksActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void prepareMenu(Menu menu, int id){
        Log.d(TAG, "prepareMenu: " + getResources().getResourceEntryName(id));
        MenuItem menuItem;
        for (int i = 0; i < menu.size(); i++) {
            if ((menuItem = menu.getItem(i)).getIcon()!=null){
                menuItem.getIcon().setTint(Color.BLACK);
            }
        }
        menuItem = menu.findItem(id);
        menuItem.getIcon().setTint(ContextCompat.getColor(this,R.color.colorAccent));
        menuItem.setEnabled(false);
        invalidateOptionsMenu();
    }
}
