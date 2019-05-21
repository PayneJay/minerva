package com.minerva.business.article.list.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.article.list.IUnLoginListener;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.network.RetrofitHelper;
import com.minerva.network.RetrofitService;
import com.minerva.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class ArticleModel {
    private static ArticleModel instance;
    private List<ArticleType> mTypeList = new ArrayList<>();
    private Map<String, Integer> languageMap = new HashMap<>();
    private DiffObservableList<BaseViewModel> mData;

    private ArticleModel() {
        mTypeList.clear();
        mTypeList.addAll(getTabTypes());
        languageMap.clear();
        languageMap.putAll(getSupportLanguage());
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
     * @param lang     语言
     * @param lastID   上一页最后一条id
     * @param page     当前页数
     * @param observer 网络回调
     */
    public void getArticleList(int index, int lang, String lastID, int page, IUnLoginListener listener, Observer<? super ArticleBean> observer) {
        RetrofitService server = RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null).getServer();
        if (index == 1) {//是推荐模块
            //是未登录态
            if (!GlobalData.getInstance().isLogin()) {
                if (listener != null) {
                    listener.unLogin();
                }
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
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_hot), "0"));
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_recommend), "0"));
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_science_technology), "101000000"));
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_start_up), "101040000"));
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_digital), "101050000"));
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_technology), "20"));
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_design), "108000000"));
        list.add(new ArticleType(ResourceUtil.getString(R.string.article_marketing), "114000000"));

        return list;
    }

    private Map<String, Integer> getSupportLanguage() {
        Map<String, Integer> map = new HashMap<>();
        map.put("0", 1);
        map.put("1", 1);
        map.put("2", 1);
        map.put("3", 1);
        map.put("4", 1);
        map.put("5", 1);
        map.put("6", 1);
        map.put("7", 1);

        return map;
    }

    public Map<String, Integer> getLanguageMap() {
        return languageMap;
    }

    public void setLanguageMap(Map<String, Integer> languageMap) {
        this.languageMap = languageMap;
    }

    public DiffObservableList<BaseViewModel> getData() {
        if (mData == null) {
            mData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof ArticleItemViewModel && newItem instanceof ArticleItemViewModel) {
                        return ((ArticleItemViewModel) oldItem).articleID.equalsIgnoreCase(((ArticleItemViewModel) newItem).articleID);
                    }
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }
        return mData;
    }

    public void clear() {
        getData().update(new ObservableArrayList<BaseViewModel>());
    }

    public void setData(ObservableList<BaseViewModel> observableList) {
        getData().update(observableList);
    }

    public void onDestroy() {
        clear();
        instance = null;
    }
}
