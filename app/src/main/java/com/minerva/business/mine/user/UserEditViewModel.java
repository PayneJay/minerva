package com.minerva.business.mine.user;

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
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.utils.DisplayUtils;
import com.minerva.utils.ResourceUtils;
import com.minerva.utils.SPUtils;

import static android.support.v7.app.AlertDialog.Builder;
import static android.support.v7.app.AlertDialog.OnClickListener;

public class UserEditViewModel extends BaseViewModel implements EditPwdViewModel.IDialogClickListener {
    public ObservableField<String> headUrl = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>(ResourceUtils.getString(R.string.user_edit_click_to_update));
    public ObservableField<String> weibo = new ObservableField<>(ResourceUtils.getString(R.string.user_edit_click_to_relate));
    public ObservableField<String> QQ = new ObservableField<>(ResourceUtils.getString(R.string.user_edit_click_to_relate));
    public ObservableField<String> wechat = new ObservableField<>(ResourceUtils.getString(R.string.user_edit_click_to_relate));

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

    UserEditViewModel(Context context) {
        super(context);
        headUrl.set((String) SPUtils.get(context, Constants.UserInfoKey.USER_PROFILE, ""));
        userName.set((String) SPUtils.get(context, Constants.UserInfoKey.USER_NAME, "——"));
        email.set((String) SPUtils.get(context, Constants.UserInfoKey.USER_EMAIL, "——"));
        String weiboName = (String) SPUtils.get(context, Constants.UserInfoKey.WEIBO, "");
        String qqName = (String) SPUtils.get(context, Constants.UserInfoKey.QQ, "");
        String wechatName = (String) SPUtils.get(context, Constants.UserInfoKey.WECHAT, "");
        if (!TextUtils.isEmpty(weiboName)) {
            weibo.set(weiboName);
        }
        if (!TextUtils.isEmpty(qqName)) {
            QQ.set(qqName);
        }
        if (!TextUtils.isEmpty(wechatName)) {
            wechat.set(wechatName);
        }
    }

    @BindingAdapter({"toolBarMenu", "menuItemClick"})
    public static void setToolBarMenu(Toolbar toolbar, int menuRes, Toolbar.OnMenuItemClickListener menuItemClick) {
        toolbar.inflateMenu(menuRes);
        if (menuItemClick != null) {
            toolbar.setOnMenuItemClickListener(menuItemClick);
        }
    }

    /**
     * 点击头像
     */
    public void onHeadClick() {
        Constants.showToast(context);
    }

    /**
     * 点击用户名
     */
    public void onNameClick() {
        Constants.showToast(context);
    }

    /**
     * 点击邮箱
     */
    public void onEmailClick() {
        Constants.showToast(context);
    }

    /**
     * 点击密码
     */
    public void onPwdClick() {
        showEditPwdDialog();
    }

    /**
     * 点击微博
     */
    public void onWeiboClick() {
        Constants.showToast(context);
    }

    /**
     * 点击QQ
     */
    public void onQQClick() {
        Constants.showToast(context);
    }

    /**
     * 点击微信
     */
    public void onWechatClick() {
        Constants.showToast(context);
    }

    @Override
    public void confirm() {
        if (editPwdPopup != null) {
            editPwdPopup.dismiss();
        }
        Toast.makeText(context, ResourceUtils.getString(R.string.toast_update_group_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancel() {
        if (editPwdPopup != null) {
            editPwdPopup.dismiss();
        }
    }

    private void logOut() {
        GlobalData.getInstance().clear();
        restartApp();
    }

    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
        Builder builder = new Builder(context)
                .setTitle(ResourceUtils.getString(R.string.dialog_title_note))
                .setMessage(ResourceUtils.getString(R.string.dialog_are_you_sure_logout))
                .setNegativeButton(ResourceUtils.getString(R.string.dialog_cancel), null)
                .setPositiveButton(ResourceUtils.getString(R.string.dialog_confirm), new OnClickListener() {
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
     * 修改密码Dialog
     */
    private void showEditPwdDialog() {
        if (editPwdPopup == null) {
            editPwdPopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtils.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
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

        EditPwdViewModel viewModel = new EditPwdViewModel(context);
        viewModel.title.set(ResourceUtils.getString(R.string.dialog_edit_password));
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_edit_password_layout, null, false);
        binding.setVariable(BR.editPwdVM, viewModel);
        binding.executePendingBindings();
        editPwdPopup.setContentView(binding.getRoot());
        editPwdPopup.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}
