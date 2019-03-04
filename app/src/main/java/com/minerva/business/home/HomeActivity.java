package com.minerva.business.home;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.business.Main2Activity;
import com.minerva.business.search.SearchActivity;
import com.minerva.common.Constants;
import com.minerva.utils.ResouceUtils;

public class HomeActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private MenuItem mMenuItem;
    private Toolbar mToolbar;
    private boolean isExit;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mMenuItem = item;
            mToolbar.setTitle(item.getTitle());
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_profile:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    private PopupMenu.OnMenuItemClickListener mMoreMenuClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Constants.showToast(HomeActivity.this);
            return true;
        }
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mMenuItem != null) {
                mMenuItem.setChecked(false);
            } else {
                mNavigationView.getMenu().getItem(0).setChecked(false);
            }
            mMenuItem = mNavigationView.getMenu().getItem(position);
            mMenuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestNeedPermissions();
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);

        mViewPager = findViewById(R.id.viewpager);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setUpViewPager();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mySearch:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.myMore:
                showPopupMenu();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, mToolbar.getMenu());
        return true;
    }

    /**
     * double quite the app
     */
    private void exitByDoubleClick() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, ResouceUtils.getString(R.string.exit_by_double_click), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            Intent intent = new Intent(Constants.Activity.ROOT_FILTER);
            intent.putExtra(Constants.Activity.ROOT_UPDATE,
                    Constants.Activity.ROOT_CLOSE_APP);
            sendBroadcast(intent);
            ActivityManager acManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
            if (acManager != null) {
                acManager.killBackgroundProcesses(getPackageName());
            }
            finish();
        }
    }

    private void requestNeedPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    private void setUpViewPager() {
        HomeViewPagerFragmentAdapter adapter = new HomeViewPagerFragmentAdapter(getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setAdapter(adapter);
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, mToolbar);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(mMoreMenuClickListener);
        mToolbar.setOnTouchListener(popupMenu.getDragToOpenListener());
        popupMenu.show();
    }
}
