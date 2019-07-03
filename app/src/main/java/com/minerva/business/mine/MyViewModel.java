package com.minerva.business.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.detail.model.ArticleDetailModel;
import com.minerva.business.article.list.model.ArticleListBean;
import com.minerva.business.guide.GuideSubscribeActivity;
import com.minerva.business.mine.about.AboutActivity;
import com.minerva.business.mine.collection.MyCollectionActivity;
import com.minerva.business.mine.journal.MyJournalActivity;
import com.minerva.business.mine.message.MessageActivity;
import com.minerva.business.mine.read.ReadLaterActivity;
import com.minerva.business.mine.read.model.ReadModel;
import com.minerva.business.mine.signinout.LoginActivity;
import com.minerva.business.mine.user.UserEditActivity;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.common.GlobalData;
import com.minerva.db.Article;
import com.minerva.db.User;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResourceUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.minerva.common.Constants.showToast;

public class MyViewModel extends BaseViewModel {
    public ObservableField<String> userName = new ObservableField<>(ResourceUtil.getString(R.string.mine_click_login));
    public ObservableField<String> headUrl = new ObservableField<>();
    public ObservableField<String> unReadCount = new ObservableField<>("");

    MyViewModel(Context context) {
        super(context);
        EventBus.getDefault().register(this);
        updateStatus();
    }

    public void goLogin() {
        if (GlobalData.getInstance().isLogin()) {
            context.startActivity(new Intent(context, GuideSubscribeActivity.class));
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
        if (GlobalData.getInstance().isLogin()) {
            context.startActivity(new Intent(context, MyJournalActivity.class));
            return;
        }

        Toast.makeText(context, ResourceUtil.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
    }

    public void goNotification() {
        if (GlobalData.getInstance().isLogin()) {
            context.startActivity(new Intent(context, MessageActivity.class));
            return;
        }

        Toast.makeText(context, ResourceUtil.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(context, ResourceUtil.getString(R.string.toast_already_new), Toast.LENGTH_SHORT).show();
    }

    public void goFeedback() {
        showToast(context);
    }

    public void aboutUs() {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    @Override
    public void onVisible(boolean isVisibleToUser) {
        super.onVisible(isVisibleToUser);
        if (isVisibleToUser) {
            setUnreadPoint();
            updateStatus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUnreadPoint();
        updateStatus();
    }

    @Override
    public void onEvent(EventMsg eventMsg) {
        super.onEvent(eventMsg);
        if (TextUtils.equals(eventMsg.getMsg(), Constants.EventMsgKey.LOGIN_SUCCESS)) {
            updateStatus();
        }
    }

    private void setUnreadPoint() {
        if (GlobalData.getInstance().isLogin()) {
            setUnReadByServer();
        } else {
            setUnReadByLocal();
        }
    }

    private void setUnReadByServer() {
        ReadModel.getInstance().getLateList(new NetworkObserver<ArticleListBean>() {
            @Override
            public void onSuccess(ArticleListBean articleListBean) {
                List<ArticleDetailBean.ArticleBean> articles = articleListBean.getArticles();
                int size = (articles == null) ? 0 : articles.size();
                setUnReadCount(size);
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    private void setUnReadCount(int size) {
        unReadCount.set(size > 0 ? String.valueOf(size) : "");
        if (size > 0 && size < 100) {
            unReadCount.set(String.valueOf(size));
        } else if (size > 99) {
            unReadCount.set("99+");
        } else {
            unReadCount.set("");
        }
    }

    private void setUnReadByLocal() {
        List<Article> articles = ArticleDetailModel.getInstance().loadAllByType(0);
        setUnReadCount(articles.size());
    }

    /**
     * 更新状态
     */
    private void updateStatus() {
        if (GlobalData.getInstance().isLogin()) {
            User user = GlobalData.getInstance().getUser();
            headUrl.set(user.getProfile());
            userName.set(user.getName());
        }
    }

}
