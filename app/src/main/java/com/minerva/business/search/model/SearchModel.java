package com.minerva.business.search.model;

import android.content.Context;
import android.text.TextUtils;

import com.minerva.R;
import com.minerva.business.category.book.model.AllBook;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.SPUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchModel {
    private static SearchModel instance;
    private List<String> searchHistory = new ArrayList<>();
    private String[] tabTitles = new String[]{ResourceUtil.getString(R.string.tab_article), ResourceUtil.getString(R.string.tab_site), ResourceUtil.getString(R.string.tab_book)};

    public static SearchModel getInstance() {
        if (instance == null) {
            instance = new SearchModel();
        }
        return instance;
    }

    /**
     * 根据关键字搜索文章列表
     *
     * @param page     当前页码
     * @param keyWord  搜索关键字
     * @param observer 回调
     */
    public void searchArticleByKW(int page, String keyWord, Observer<? super ArticleResult> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .searchByKeyWord(page, keyWord, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 根据关键字搜索站点列表
     *
     * @param keyWord  搜索关键字
     * @param observer 回调
     */
    public void searchSiteByKW(String keyWord, Observer<? super SiteResult> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .searchByKeyWord(keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 根据关键字搜索图书列表
     *
     * @param page     当前页码
     * @param keyWord  搜索关键字
     * @param observer 回调
     */
    public void searchBookByKW(int page, String keyWord, Observer<? super AllBook> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .searchByKeyWord(page, keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取搜索关键字历史列表
     *
     * @param context context
     * @return 搜索列表
     */
    public List<String> getSearchHistory(Context context) {
        String history = (String) SPUtil.get(context, Constants.KeyExtra.SEARCH_HISTORY_KEYWORD, "");
        if (TextUtils.isEmpty(history)) {
            return searchHistory;
        }

        searchHistory.clear();
        searchHistory.addAll(CommonUtil.json2List(history));
        return searchHistory;
    }

    /**
     * 设置搜索关键字历史列表
     *
     * @param context       context
     * @param searchHistory 搜索历史
     */
    public void setSearchHistory(Context context, List<String> searchHistory) {
        String history = CommonUtil.toJson(searchHistory);
        SPUtil.put(context, Constants.KeyExtra.SEARCH_HISTORY_KEYWORD, history);
    }

    public List<String> getTabTitle() {
        return Arrays.asList(tabTitles);
    }
}
