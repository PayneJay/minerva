package com.minerva.business.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.business.home.sort.SiteSortActivity;
import com.minerva.business.home.subscribe.SubscribeSiteActivity;
import com.minerva.business.home.weekly.WeeklyActivity;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.business.mine.signinout.model.UserInfo;
import com.minerva.business.search.SearchActivity;
import com.minerva.business.settings.RecommendActivity;
import com.minerva.business.settings.SettingsActivity;
import com.minerva.business.site.menu.CreateGroupViewModel;
import com.minerva.business.site.menu.model.MenuModel;
import com.minerva.business.site.model.SiteModel;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.common.GlobalData;
import com.minerva.network.NetworkObserver;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.DisplayUtils;
import com.minerva.utils.ResourceUtils;
import com.minerva.widget.Loading;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, CreateGroupViewModel.IDialogClickListener {
    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private MenuItem mNavMenuItem;
    private MenuItem menuSearch, menuMore, menuSettings;
    private Toolbar mToolbar;
    private PopupWindow createPopup;
    private Loading loading;
    private Context context;
    private boolean isExit;

    //底部导航Tab点击事件处理
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mNavMenuItem = item;
            mToolbar.setTitle(item.getTitle());
            mToolbar.setVisibility(View.VISIBLE);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setCurrentPage(0, true, false);
                    return true;
                case R.id.navigation_dashboard:
                    setCurrentPage(1, true, false);
                    return true;
                case R.id.navigation_notifications:
                    setCurrentPage(2, false, true);
                    mToolbar.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_profile:
                    setCurrentPage(3, false, true);
                    return true;
            }
            return false;
        }
    };

    //右上角菜单项点击事件处理
    private PopupMenu.OnMenuItemClickListener mMoreMenuClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.toolbar_more_chinese:
                    EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.SELECT_ARTICLE_LANGUAGE, 1));
                    break;
                case R.id.toolbar_more_english:
                    EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.SELECT_ARTICLE_LANGUAGE, 2));
                    break;
                case R.id.toolbar_more_mix:
                    EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.SELECT_ARTICLE_LANGUAGE, 0));
                    break;
                case R.id.toolbar_more_recommend_settings:
                    goReadSetting();
                    break;
//                case R.id.toolbar_more_custom_channel:
//                    Constants.showToast(context);
//                    break;
                case R.id.toolbar_more_week_list:
                    goWeekly();
                    break;
                case R.id.toolbar_subscribe_discover:
                    goSubscribeDiscover();
                    break;
                case R.id.toolbar_create_group:
                    showCreateGroupDialog();
                    break;
                case R.id.toolbar_sort_group:
                    goSortGroups();
                    break;
                case R.id.toolbar_all_read:
                    markAllRead();
                    break;
                case R.id.toolbar_use_tips:
                    showUseTips();
                    break;
                default:
                    Constants.showToast(context);
                    break;
            }
            return true;
        }
    };

    //Tab页面切换监听
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mNavMenuItem != null) {
                mNavMenuItem.setChecked(false);
            } else {
                mNavigationView.getMenu().getItem(0).setChecked(false);
            }
            mNavMenuItem = mNavigationView.getMenu().getItem(position);
            mNavMenuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        requestNeedPermissions();
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);

        mViewPager = findViewById(R.id.viewpager);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setUpViewPager();
        getUserInfo();
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
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra(Constants.KeyExtra.EXTRA_TAB, mViewPager.getCurrentItem());
                startActivity(intent);
                break;
            case R.id.myMore:
                showPopupMenu();
                break;
            case R.id.mySettings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, mToolbar.getMenu());
        menuSearch = menu.findItem(R.id.mySearch);
        menuMore = menu.findItem(R.id.myMore);
        menuSettings = menu.findItem(R.id.mySettings);
        menuSearch.setVisible(true);
        menuMore.setVisible(true);
        menuSettings.setVisible(false);
        mToolbar.setTitle(mNavigationView.getMenu().getItem(0).getTitle());
        return true;
    }

    @Override
    public void confirm(String name) {
        if (createPopup != null) {
            createPopup.dismiss();
        }

        if (loading == null) {
            loading = new Loading.Builder(this).show();
        }
        MenuModel.getInstance().createGroup(name, new NetworkObserver<SitesBean>() {
            @Override
            public void onSuccess(SitesBean sitesBean) {
                loading.dismiss();
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_create_group_success), Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(sitesBean);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    @Override
    public void cancel() {
        if (createPopup != null) {
            createPopup.dismiss();
        }
    }

    /**
     * double quite the app
     */
    private void exitByDoubleClick() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, ResourceUtils.getString(R.string.exit_by_double_click), Toast.LENGTH_SHORT).show();
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

    /**
     * 请求必须权限
     */
    private void requestNeedPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    /**
     * 设置当前页面
     *
     * @param tab tab
     * @param b   搜索icon是否显示
     * @param b2  设置icon是否显示
     */
    private void setCurrentPage(int tab, boolean b, boolean b2) {
        mViewPager.setCurrentItem(tab);
        menuSearch.setVisible(b);
        menuMore.setVisible(b);
        menuSettings.setVisible(b2);
    }

    /**
     * 设置ViewPager
     */
    private void setUpViewPager() {
        HomeViewPagerFragmentAdapter adapter = new HomeViewPagerFragmentAdapter(getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setAdapter(adapter);
    }

    /**
     * 显示菜单popup
     */
    @SuppressLint("ClickableViewAccessibility")
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, mToolbar);
        switch (mViewPager.getCurrentItem()) {
            case 0:
                popupMenu.getMenuInflater().inflate(R.menu.article_popup_menu, popupMenu.getMenu());
                break;
            case 1:
                popupMenu.getMenuInflater().inflate(R.menu.site_popup_menu, popupMenu.getMenu());
                break;
        }
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(mMoreMenuClickListener);
        mToolbar.setOnTouchListener(popupMenu.getDragToOpenListener());
        popupMenu.show();
    }

    /**
     * 显示创建分组Dialog
     */
    private void showCreateGroupDialog() {
        if (!GlobalData.getInstance().isLogin()) {
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
            return;
        }
        if (createPopup == null) {
            createPopup = new PopupWindow(getWindow().getDecorView(), DisplayUtils.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            createPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);
                }
            });
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.6f;
        getWindow().setAttributes(lp);

        CreateGroupViewModel viewModel = new CreateGroupViewModel(this);
        viewModel.title.set(ResourceUtils.getString(R.string.dialog_create_group));
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_create_group_layout, null, false);
        binding.setVariable(BR.createGroupVM, viewModel);
        binding.executePendingBindings();
        createPopup.setContentView(binding.getRoot());
        createPopup.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 跳转订阅发现
     */
    private void goSubscribeDiscover() {
        startActivity(new Intent(this, SubscribeSiteActivity.class));
    }

    /**
     * 跳转分组排序
     */
    private void goSortGroups() {
        if (!GlobalData.getInstance().isLogin()) {
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
            return;
        }
        List<SitesBean.ItemsBeanX> itemList = SiteModel.getInstance().getItemList();
        if (itemList.size() <= 1) {
            Toast.makeText(this, ResourceUtils.getString(R.string.toast_please_create_more_to_sort), Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, SiteSortActivity.class));
    }

    /**
     * 全部标为已读
     */
    private void markAllRead() {
        if (!GlobalData.getInstance().isLogin()) {
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
            return;
        }
        if (loading == null) {
            loading = new Loading.Builder(this).show();
        }

        MenuModel.getInstance().markAllRead(new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_already_update), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    /**
     * 显示使用提示
     */
    private void showUseTips() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(ResourceUtils.getString(R.string.dialog_title_note))
                .setMessage(ResourceUtils.getString(R.string.dialog_use_tips_content))
                .setPositiveButton(ResourceUtils.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    /**
     * 跳转阅读设置
     */
    private void goReadSetting() {
        if (GlobalData.getInstance().isLogin()) {
            startActivity(new Intent(this, RecommendActivity.class));
            return;
        }

        Toast.makeText(this, ResourceUtils.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转一周拾遗
     */
    private void goWeekly() {
        if (GlobalData.getInstance().isLogin()) {
            startActivity(new Intent(this, WeeklyActivity.class));
            return;
        }

        Toast.makeText(this, ResourceUtils.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        UserInfo.UserBean user = userInfo.getUser();
                        if (user != null) {
                            LoginRegisterModel.getInstance().saveUserInfo(context, user);
                            EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.LOGIN_SUCCESS));
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
    }
}
