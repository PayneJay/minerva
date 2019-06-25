package com.minerva.business.search.model;

import com.minerva.MinervaApp;
import com.minerva.R;
import com.minerva.business.category.book.model.AllBook;
import com.minerva.common.Constants;
import com.minerva.db.SearchHistory;
import com.minerva.db.SearchHistoryDao;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ResourceUtil;

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
     * @return 搜索列表
     */
    public List<SearchHistory> getSearchHistory() {
        SearchHistoryDao historyDao = ((MinervaApp) Constants.application).getDaoSession().getSearchHistoryDao();
        return historyDao.queryBuilder().orderDesc(SearchHistoryDao.Properties.Timestamp).build().list();
    }

    /**
     * 设置搜索关键字历史列表
     *
     * @param query 搜索关键字
     */
    public void setSearchHistory(String query) {
        SearchHistoryDao historyDao = ((MinervaApp) Constants.application).getDaoSession().getSearchHistoryDao();
        historyDao.insertOrReplace(new SearchHistory(query.trim(), System.currentTimeMillis()));
    }

    /**
     * 删除历史
     *
     * @param key 关键字
     */
    public void deleteHistoryByKey(String key) {
        SearchHistoryDao historyDao = ((MinervaApp) Constants.application).getDaoSession().getSearchHistoryDao();
        historyDao.deleteByKey(key);
    }

    public List<String> getTabTitle() {
        return Arrays.asList(tabTitles);
    }
}
