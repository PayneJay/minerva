package com.minerva.business.mine.collection;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.business.article.list.ArticleListViewModel;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.mine.collection.model.CollectionModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.common.GlobalData;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;

import org.greenrobot.eventbus.EventBus;

public class CollectionViewModel extends ArticleListViewModel {
    public String mTitle;
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    CollectionViewModel(Context context) {
        super(context, context.getClass().getSimpleName());
        EventBus.getDefault().register(this);
        mTitle = ResourceUtil.getString(R.string.mine_collection);
        requestServer();
    }

    @Override
    protected void requestServer() {
        if (!GlobalData.getInstance().isLogin()) {
            setEmptyPage();
            return;
        }

        if (!CommonUtil.isNetworkAvailable(context)) {
            setNetworkError();
            return;
        }

        CollectionModel.getInstance().getMyCollections(mCurrentPage, new NetworkObserver<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                refreshing.set(false);
                if (articleBean == null || articleBean.getArticles().size() <= 0) {
                    setEmptyPage();
                    return;
                }

                handleData(articleBean);
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
            }
        });
    }

    @Override
    public void onEvent(EventMsg eventMsg) {
        if (TextUtils.equals(eventMsg.getMsg(), Constants.EventMsgKey.CANCEL_FAVORITE_ARTICLE)) {
            requestServer();
        }
    }

    /**
     * 设置空页面
     */
    private void setEmptyPage() {
        refreshing.set(false);
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        items.clear();
        mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
        items.add(mBlankVM);
    }

    /**
     * 设置网络错误页
     */
    private void setNetworkError() {
        refreshing.set(false);
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        if (mCurrentPage == 0) {
            items.clear();
            mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
            items.add(mBlankVM);
        }
    }
}
