package com.minerva.business.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.about.AboutActivity;
import com.minerva.business.mine.collection.MyCollectionActivity;
import com.minerva.business.mine.login.LoginActivity;
import com.minerva.business.mine.read.ReadLaterActivity;
import com.minerva.business.mine.user.UserEditActivity;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.common.GlobalData;
import com.minerva.utils.ResouceUtils;
import com.minerva.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.minerva.common.Constants.showToast;

public class MyViewModel extends BaseViewModel {
    public ObservableField<String> userName = new ObservableField<>(ResouceUtils.getString(R.string.mine_click_login));
    public ObservableField<String> headUrl = new ObservableField<>();

    MyViewModel(Context context) {
        super(context);
        EventBus.getDefault().register(this);
        updateStatus();
    }

    public void goLogin() {
        if (GlobalData.getInstance().isLogin()) {
            context.startActivity(new Intent(context, UserEditActivity.class));
            return;
        }
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void goUnRead() {
        Intent intent = new Intent(context, ReadLaterActivity.class);
        intent.putExtra(Constants.KeyExtra.COME_FROM_MINE, Constants.KeyExtra.READ_LATER_MAP);
        context.startActivity(intent);
    }

    public void goCollection() {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        context.startActivity(intent);
    }

    public void goJournal() {
        showToast(context);
    }

    public void goNotification() {
        showToast(context);
    }

    public void goHistory() {
        Intent intent = new Intent(context, ReadLaterActivity.class);
        intent.putExtra(Constants.KeyExtra.COME_FROM_MINE, Constants.KeyExtra.READ_HISTORY_MAP);
        context.startActivity(intent);
    }

    public void switchModel() {
        showToast(context);
    }

    public void readOffline() {
        showToast(context);
    }

    public void checkUpgrade() {
        showToast(context);
    }

    public void goFeedback() {
        showToast(context);
    }

    public void aboutUs() {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    @Override
    public void onEvent(EventMsg eventMsg) {
        super.onEvent(eventMsg);
        if (TextUtils.equals(eventMsg.getMsg(), Constants.EventMsgKey.LOGIN_SUCCESS)) {
            updateStatus();
        }
    }

    /**
     * 更新状态
     */
    private void updateStatus() {
        if (GlobalData.getInstance().isLogin()) {
            headUrl.set((String) SPUtils.get(context, Constants.UserInfoKey.USER_PROFILE, ""));
            userName.set((String) SPUtils.get(context, Constants.UserInfoKey.USER_NAME, "——"));
        }
    }

}
