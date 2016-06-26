package com.example.marwaadel.shopownertablet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marwaadel.shopownertablet.utils.Constants;

/**
 * Created by M.3ADL on 6/23/2016.
 */
public class BaseActivity extends AppCompatActivity {
protected String mUserID, mUid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);

        mUserID =sp.getString(Constants.KEY_UID,null);
        mUid=sp.getString(Constants.FETCH__UID,null);
        Log.d("fetched uid",mUid);
        Constants.My_UID=mUid;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            super.onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
