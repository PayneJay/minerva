package com.minerva.business.mine.read;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.detail.model.ArticleDetailModel;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.article.list.ArticleListViewModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReadLaterViewModel extends ArticleListViewModel {
    public ObservableField<String> mTitle = new ObservableField<>(ResourceUtils.getString(R.string.mine_to_be_read));
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    private List<ArticleDetailBean.ArticleBean> mData = new ArrayList<>();
    private BlankViewModel mBlankVM;
    private Disposable mDisposable;
    private String mKey;

    ReadLaterViewModel(Context context) {
        super(context, context.getClass().getSimpleName());
        mKey = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COME_FROM_MINE);
        setTitle();
        setReadLaterData(context);
        createViewModel();
    }

    @Override
    protected void createViewModel() {
        items.clear();

        for (int i = 0; i < mData.size(); i++) {
            ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
            ArticleDetailBean.ArticleBean articlesBean = mData.get(i);
            viewModel.content.set(articlesBean.getTitle());
            viewModel.date.set(articlesBean.getTime());
            viewModel.imgUrl.set(articlesBean.getImg());
            viewModel.articleID = articlesBean.getId();
            items.add(viewModel);
        }
    }

    @Override
    protected void requestServer() {
        Observable.just("Success")
                //延时三秒，第一个参数是数值，第二个参数是事件单位
                .delay(1, TimeUnit.SECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        refreshing.set(false);
                    }
                });
    }

    @Override
    public void onDetach() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDetach();
    }

    /**
     * 设置本地缓存的待读数据
     *
     * @param context context
     */
    private void setReadLaterData(Context context) {
        Map<String, Object> readLater = ArticleDetailModel.getInstance().getArticlesByKey(context, mKey);
        if (readLater.size() <= 0) {
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
            items.add(mBlankVM);
            return;
        }

        for (Object obj : readLater.values()) {
            if (obj instanceof ArticleDetailBean.ArticleBean) {
                mData.add((ArticleDetailBean.ArticleBean) obj);
            }
        }
    }

    private void setTitle() {
        switch (mKey) {
            case Constants.KeyExtra.READ_LATER_MAP:
                mTitle.set(ResourceUtils.getString(R.string.mine_to_be_read));
                break;
            case Constants.KeyExtra.READ_HISTORY_MAP:
                mTitle.set(ResourceUtils.getString(R.string.mine_read_history));
                break;
        }
    }
}
