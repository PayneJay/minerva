package com.minerva.business.mine.journal.kan.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class FavKanModel {
    private static FavKanModel instance;
    private DiffObservableList<BaseViewModel> mData;

    public static FavKanModel getInstance() {
        if (instance == null) {
            instance = new FavKanModel();
        }
        return instance;
    }

    /**
     * 根据id获取对应收藏列表
     *
     * @param id       收藏组别id
     * @param observer 回调
     */
    public void getFavKansById(String id, Observer<? super FavKanBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getFavKansById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 迁移推刊
     *
     * @param id       要迁移的推刊id
     * @param targetId 目标推刊id
     * @param observer 回调
     */
    public void migrateKan(String id, String targetId, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .migrateKan(id, targetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 更新推刊信息
     *
     * @param id       修改推刊id
     * @param name     推刊名称
     * @param desc     推刊描述
     * @param type     是否仅自己可见
     * @param observer 回调
     */
    public void updateKan(String id, String name, String desc, int type, Observer<? super KanBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .updateKan(id, name, desc, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 根据id删除推刊
     *
     * @param id       推刊id
     * @param observer 回调
     */
    public void deleteKanById(String id, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .deleteKanById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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

    /**
     * @return 过滤Blank类型item
     */
    public ObservableList<BaseViewModel> getItemsFilterBlank() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        for (BaseViewModel viewModel : getData()) {
            if (viewModel instanceof ArticleItemViewModel) {
                temp.add(viewModel);
            }
        }
        return temp;
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
