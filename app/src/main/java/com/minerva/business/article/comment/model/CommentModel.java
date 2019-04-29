package com.minerva.business.article.comment.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.comment.CommentItemViewModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class CommentModel {
    private static CommentModel instance;
    private DiffObservableList<BaseViewModel> mData;

    public static CommentModel getInstance() {
        if (instance == null) {
            instance = new CommentModel();
        }
        return instance;
    }

    /**
     * 获取文章评论列表
     *
     * @param aid      文章ID
     * @param pn       当前页数
     * @param observer 回调
     */
    public void getCommentsByAid(String aid, int pn, Observer<? super CommentListBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getCommentsByAid(aid, 0, pn, 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取评论观察者
     *
     * @param aid 文章id
     * @param pn  页码
     * @return observable
     */
    public Observable<CommentListBean> getCommentsObservable(String aid, int pn) {
        return RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getCommentsByAid(aid, 0, pn, 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 加订阅
     *
     * @param aid     站点id
     * @param content 内容
     */
    public Observable<CommentDetail> submitComment(String aid, String content) {
        return RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .submitComment(aid, content, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 删除订单
     *
     * @param id 评论id
     */
    public Observable<BaseBean> deleteCommentObservable(String id) {
        return RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .deleteCommentById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DiffObservableList<BaseViewModel> getData() {
        if (mData == null) {
            mData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof CommentItemViewModel && newItem instanceof CommentItemViewModel) {
                        return ((CommentItemViewModel) oldItem).getId().equalsIgnoreCase(((CommentItemViewModel) newItem).getId());
                    }
                    if (oldItem instanceof BlankViewModel && newItem instanceof BlankViewModel) {
                        return ((BlankViewModel) oldItem).getId().equalsIgnoreCase(((BlankViewModel) newItem).getId());
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
