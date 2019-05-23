package com.minerva.business.home.weekly;

import android.content.Context;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.category.mag.MagTitleViewModel;
import com.minerva.business.home.weekly.model.WeekDetailBean;
import com.minerva.business.home.weekly.model.WeeklyModel;
import com.minerva.business.site.PolymerReadViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.DateUtil;
import com.minerva.utils.ResourceUtil;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

public class WeeklyDetailViewModel extends PolymerReadViewModel {
    private String weekId;
    private long weekTime;

    WeeklyDetailViewModel(Context context) {
        super(context, context.getClass().getSimpleName());
        weekId = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.WEEKLY_ID);
        weekTime = ((BaseActivity) context).getIntent().getLongExtra(Constants.KeyExtra.WEEKLY_DATE, 0);
        MagTitleViewModel titleViewModel = new MagTitleViewModel(context);
        titleViewModel.title.set(ResourceUtil.getString(R.string.weekly_tag_gleaning));
        name = getDateText();
        titleViewModel.name.set(name);
        items.add(titleViewModel);
        requestServer();
    }

    @Override
    protected void requestServer() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            refreshing.set(false);
            setNetworkError();
            return;
        }

        WeeklyModel.getInstance().getWeeklyDetail(weekId, new NetworkObserver<WeekDetailBean>() {
            @Override
            public void onSuccess(WeekDetailBean weekDetailBean) {
                refreshing.set(false);
                List<ArticleDetailBean.ArticleBean> articles = weekDetailBean.getArticles();
                mData.clear();
                mData.addAll(articles);
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
            }
        });
    }

    /**
     * 格式化日期
     *
     * @return
     */
    private String getDateText() {
        int month = DateUtil.getMonthByDate(new Date(weekTime));
        int weekOfMonth = DateUtil.getWeekOfMonth(new Date(weekTime));
        return MessageFormat.format(ResourceUtil.getString(R.string.weekly_month_week), month, weekOfMonth);
    }
}
