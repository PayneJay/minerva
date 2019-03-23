package com.minerva.business.article.list.model;

import com.minerva.R;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.network.RetrofitHelper;
import com.minerva.network.RetrofitService;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.ResouceUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleModel {
    private static ArticleModel instance;
    private List<ArticleType> mTypeList = new ArrayList<>();

    private ArticleModel() {
        mTypeList.clear();
        mTypeList.addAll(getTabTypes());
    }

    public static ArticleModel getInstance() {
        if (instance == null) {
            instance = new ArticleModel();
        }
        return instance;
    }

    /**
     * 获取文章列表
     *
     * @param index    当前tab位置
     * @param lang
     * @param lastID   上一页最后一条id
     * @param page     当前页数
     * @param observer 网络回调
     */
    public void getArticleList(int index, int lang, String lastID, int page, Observer<? super ArticleBean> observer) {
        RetrofitService server = RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null).getServer();
        if (index == 1) {//是推荐模块
            //是未登录态
            if (!GlobalData.getInstance().isLogin()) {
                return;
            }

            server.getRecArticles(30, lang, "0", 1, lastID, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
            return;
        }

        //其他模块
        server.getHotArticles(30, lang, mTypeList.get(index).getTabId(), 1, lastID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 获取文章Tab类型
     *
     * @return
     */
    public List<ArticleType> getTabTypes() {
        List<ArticleType> list = new ArrayList<>();
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_hot), "0"));
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_recommend), "0"));
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_science_technology), "101000000"));
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_start_up), "101040000"));
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_digital), "101050000"));
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_technology), "20"));
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_design), "108000000"));
        list.add(new ArticleType(ResouceUtils.getString(R.string.article_marketing), "114000000"));

        return list;
    }

}
