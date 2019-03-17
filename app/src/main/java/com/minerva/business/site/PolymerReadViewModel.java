package com.minerva.business.site;

import android.content.Context;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.category.mag.MagTitleViewModel;
import com.minerva.business.site.detail.PeriodicalDetailViewModel;
import com.minerva.business.site.model.PolymerRead;
import com.minerva.business.site.model.SiteModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResouceUtils;

import java.util.Iterator;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class PolymerReadViewModel extends PeriodicalDetailViewModel {
    public OnItemBind<BaseViewModel> polymerizeItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.MAG_TITLE_TYPE:
                    itemBinding.set(BR.magTitleVM, R.layout.item_mag_title_layout);
                    break;
                case Constants.RecyclerItemType.ARTICLE_COMMON_TYPE:
                    itemBinding.set(BR.articleItemVM, R.layout.item_article_common_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    private String mCode;
    private int id;

    PolymerReadViewModel(Context context) {
        super(context);
        id = ((BaseActivity) context).getIntent().getIntExtra(Constants.KeyExtra.POLYMER_ID, 0);

        MagTitleViewModel titleViewModel = new MagTitleViewModel(context);
        titleViewModel.title.set(ResouceUtils.getString(R.string.polymer_read).substring(0, 1));
        titleViewModel.name.set(name);
        items.add(titleViewModel);
    }

    @Override
    protected void requestServer() {
        SiteModel.getInstance().getPolymerReadList(id, mCurrentPage, mCode, new NetworkObserver<PolymerRead>() {
            @Override
            public void onSuccess(PolymerRead polymerRead) {
                refreshing.set(false);
                mCode = polymerRead.getCode();

                List<ArticleBean.ArticlesBean> articles = polymerRead.getArticles();
                mData.clear();
                mData.addAll(articles);
                createViewModel();
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    @Override
    protected void createViewModel() {
        if (mCurrentPage == 0) {
            Iterator<BaseViewModel> iterator = items.iterator();
            while (iterator.hasNext()) {
                BaseViewModel viewModel = iterator.next();
                if (Constants.RecyclerItemType.MAG_TITLE_TYPE != viewModel.getViewType()) {
                    iterator.remove();
                }
            }
        }

        for (int i = 0; i < mData.size(); i++) {
            ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
            ArticleBean.ArticlesBean articlesBean = mData.get(i);
            viewModel.content.set(articlesBean.getTitle());
            viewModel.date.set(articlesBean.getRectime());
            viewModel.imgUrl.set(articlesBean.getImg());
            viewModel.articleID = articlesBean.getId();
            items.add(viewModel);
        }
    }
}
