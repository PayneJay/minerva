package com.minerva.business.site.detail;

import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.network.RetrofitService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PeriodicalModel {
    private static PeriodicalModel instance;

    public static PeriodicalModel getInstance() {
        if (instance == null) {
            instance = new PeriodicalModel();
        }
        return instance;
    }

    public void getPeriodicalDetail(String aid, Observer<? super ArticleBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getPeriodicalDetail(aid, 0, 30, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
