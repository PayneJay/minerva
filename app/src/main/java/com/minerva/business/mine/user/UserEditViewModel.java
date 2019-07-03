package com.minerva.business.mine.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Process;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.SplashActivity;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.business.mine.signinout.model.UserInfo;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.db.User;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.DisplayUtil;
import com.minerva.utils.ResourceUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import static android.support.v7.app.AlertDialog.Builder;
import static android.support.v7.app.AlertDialog.OnClickListener;

public class UserEditViewModel extends BaseViewModel implements IDialogClickListener {
    public ObservableField<String> headUrl = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>(ResourceUtil.getString(R.string.user_edit_click_to_update));
    public ObservableField<String> weibo = new ObservableField<>();
    public ObservableField<String> QQ = new ObservableField<>();
    public ObservableField<String> wechat = new ObservableField<>();

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.logout) {
                showDialog();
            }
            return true;
        }
    };
    private PopupWindow editPwdPopup;
    private ProgressDialog mProgressDialog;
    private SHARE_MEDIA socialType;
    private UMAuthListener oauthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.e(Constants.TAG, "media : " + share_media.getName());
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Log.e(Constants.TAG, i + " 成功：" + share_media.getName());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.e(Constants.TAG, i + " 失败：" + share_media.getName() + "——" + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Log.e(Constants.TAG, i + " 取消：" + share_media.getName());
        }
    };

    UserEditViewModel(Context context) {
        super(context);
        RefreshView();
    }

    @BindingAdapter({"toolBarMenu", "menuItemClick"})
    public static void setToolBarMenu(Toolbar toolbar, int menuRes, Toolbar.OnMenuItemClickListener menuItemClick) {
        if (menuRes != 0) {
            toolbar.inflateMenu(menuRes);
        }
        if (menuItemClick != null) {
            toolbar.setOnMenuItemClickListener(menuItemClick);
        }
    }

    /**
     * 点击头像
     */
    public void onHeadClick() {
//        Constants.showToast(context);
    }

    /**
     * 点击用户名
     */
    public void onNameClick() {
        showUpdateUserInfoDialog("nickName");
    }

    /**
     * 点击邮箱
     */
    public void onEmailClick() {
        showUpdateUserInfoDialog("email");
    }

    /**
     * 点击密码
     */
    public void onPwdClick() {
        showUpdateUserInfoDialog("password");
    }

    /**
     * 点击微博
     */
    public void onWeiboClick() {
        User user = GlobalData.getInstance().getUser();
        if (TextUtils.isEmpty(user.getWeibo_name())) {
            socialType = SHARE_MEDIA.SINA;
            bindSocial();
        } else {
            unbindSocial();
        }
    }

    /**
     * 点击QQ
     */
    public void onQQClick() {
        User user = GlobalData.getInstance().getUser();
        socialType = SHARE_MEDIA.QQ;
        if (TextUtils.isEmpty(user.getQq_name())) {
            bindSocial();
        } else {
            unbindSocial();
        }
    }

    /**
     * 点击微信
     */
    public void onWechatClick() {
        User user = GlobalData.getInstance().getUser();
        if (TextUtils.isEmpty(user.getWeixin_name())) {
            socialType = SHARE_MEDIA.WEIXIN;
            bindSocial();
        } else {
            unbindSocial();
        }
    }

    @Override
    public void confirm() {
        RefreshView();
        if (editPwdPopup != null) {
            editPwdPopup.dismiss();
        }
        Toast.makeText(context, ResourceUtil.getString(R.string.toast_update_group_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancel() {
        if (editPwdPopup != null) {
            editPwdPopup.dismiss();
        }
    }

    private void RefreshView() {
        User user = GlobalData.getInstance().getUser();
        if (user != null) {
            headUrl.set(user.getProfile());
            userName.set(user.getName());
            email.set(TextUtils.isEmpty(user.getEmail()) ? ResourceUtil.getString(R.string.user_edit_click_to_setting) : user.getEmail());
            weibo.set(TextUtils.isEmpty(user.getWeibo_name()) ? ResourceUtil.getString(R.string.user_edit_click_to_relate) : user.getWeibo_name());
            QQ.set(TextUtils.isEmpty(user.getQq_name()) ? ResourceUtil.getString(R.string.user_edit_click_to_relate) : user.getQq_name());
            wechat.set(TextUtils.isEmpty(user.getWeixin_name()) ? ResourceUtil.getString(R.string.user_edit_click_to_relate) : user.getWeixin_name());
        }
    }

    private void logOut() {
        deleteOauth();
        GlobalData.getInstance().clear();
        restartApp();
    }

    /**
     * 三方登录解绑
     */
    private void deleteOauth() {
        User user = GlobalData.getInstance().getUser();
        switch (user.getOauth_type()) {
            case Constants.OauthType.TYPE_QQ_WEIBO:
                UMShareAPI.get(context).deleteOauth((Activity) context, SHARE_MEDIA.SINA, oauthListener);
                break;
            case Constants.OauthType.TYPE_QQ:
                UMShareAPI.get(context).deleteOauth((Activity) context, SHARE_MEDIA.QQ, oauthListener);
                break;
            case Constants.OauthType.TYPE_WEIXIN:
                UMShareAPI.get(context).deleteOauth((Activity) context, SHARE_MEDIA.WEIXIN, oauthListener);
                break;
        }
    }

    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
        Builder builder = new Builder(context)
                .setTitle(ResourceUtil.getString(R.string.dialog_title_note))
                .setMessage(ResourceUtil.getString(R.string.dialog_are_you_sure_logout))
                .setNegativeButton(ResourceUtil.getString(R.string.dialog_cancel), null)
                .setPositiveButton(ResourceUtil.getString(R.string.dialog_confirm), new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logOut();
                    }
                });
        builder.show();
    }

    private void restartApp() {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // 杀掉进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 修改用户名/邮箱/密码Dialog
     *
     * @param editType 类型
     */
    private void showUpdateUserInfoDialog(String editType) {
        if (editPwdPopup == null) {
            editPwdPopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtil.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            editPwdPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((BaseActivity) context).getWindow().setAttributes(lp);
                }
            });
        }
        WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((BaseActivity) context).getWindow().setAttributes(lp);
        ViewDataBinding binding = null;
        if (TextUtils.equals(editType, "password")) {
            binding = getUpdatePwdBinding();
        } else if (TextUtils.equals(editType, "nickName")) {
            binding = getUpdateNameBinding();
        } else if (TextUtils.equals(editType, "email")) {
            binding = getUpdateEmailBinding();
        }

        if (binding != null) {
            binding.executePendingBindings();
            editPwdPopup.setContentView(binding.getRoot());
        }
        editPwdPopup.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private ViewDataBinding getUpdateEmailBinding() {
        UpdateEmailViewModel viewModel = new UpdateEmailViewModel(context);
        viewModel.title.set(ResourceUtil.getString(R.string.dialog_edit_login_email));
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_edit_login_email_layout, null, false);
        binding.setVariable(BR.editUserEmailVM, viewModel);
        return binding;
    }

    private ViewDataBinding getUpdateNameBinding() {
        UpdateNameViewModel viewModel = new UpdateNameViewModel(context);
        viewModel.title.set(ResourceUtil.getString(R.string.dialog_edit_user_name));
        viewModel.content.set(userName.get());
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_edit_user_name_layout, null, false);
        binding.setVariable(BR.editUserNameVM, viewModel);
        return binding;
    }

    private ViewDataBinding getUpdatePwdBinding() {
        UpdatePwdViewModel viewModel = new UpdatePwdViewModel(context);
        viewModel.title.set(ResourceUtil.getString(R.string.dialog_edit_password));
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_edit_password_layout, null, false);
        binding.setVariable(BR.editPwdVM, viewModel);
        return binding;
    }

    private void bindSocial() {
        if (!UMShareAPI.get(context).isAuthorize((Activity) context, socialType)) {
            UMShareAPI.get(context).getPlatformInfo((Activity) context, socialType, oauthListener);
        }
        Log.e(Constants.TAG, "isOauth : " + UMShareAPI.get(context).isAuthorize((Activity) context, socialType));
    }

    private void unbindSocial() {
        showDialog(ResourceUtil.getString(R.string.dialog_unbinding));
        LoginRegisterModel.getInstance().cancelSocial(new NetworkObserver<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                dismissDialog();
                User user = userInfo.getUser();
                LoginRegisterModel.getInstance().updateUserInfo(user);
                RefreshView();
                Log.e(Constants.TAG, "解绑 ： " + userInfo.isSuccess());
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                Log.e(Constants.TAG, "解绑 ： " + msg);
            }
        });
    }

    private void showDialog(String text) {
        mProgressDialog = ProgressDialog.show(context, ResourceUtil.getString(R.string.dialog_title_note), text, false);
    }

    private void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
