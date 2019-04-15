package com.minerva.business.category.book;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.SpecialModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class AllBookViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> periodItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.SPECIAL_CHILD_TYPE:
                    itemBinding.set(BR.bookChildVM, R.layout.item_book_child_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    public String mTitle;
    private List<BookBean.ItemsBean.BooksBean> beanList = new ArrayList<>();
    private BlankViewModel mBlankVM;
    private int mType;
    private int mCurrentPage;
    private boolean hasNext;

    AllBookViewModel(Context context) {
        super(context);
        mType = ((BaseActivity) context).getIntent().getIntExtra(Constants.KeyExtra.COLUMN_MAG_TYPE, 0);
        mTitle = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COLUMN_MAG_TITLE);
        requestServer();
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshing.set(true);
            mCurrentPage = 0;
            requestServer();
        }
    };

    public void loadMore() {
        if (hasNext) {
            mCurrentPage++;
            requestServer();
        }
    }

    public GridLayoutManager getGlManager() {
        GridLayoutManager glManager = new GridLayoutManager(context, 3);
        glManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                try {
                    if (items.get(position).getViewType() != Constants.RecyclerItemType.SPECIAL_CHILD_TYPE) {
                        return 3;
                    } else {
                        return 1;
                    }
                } catch (Exception e) {
                    return 3;
                }
            }
        });
        return glManager;
    }

    private void requestServer() {
        if (!CommonUtils.isNetworkAvailable(context)) {
            refreshing.set(false);
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            if (mCurrentPage == 0) {
                items.clear();
                mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
                items.add(mBlankVM);
            }
            return;
        }

        SpecialModel.getInstance().getAllBookList(mType, mCurrentPage, new NetworkObserver<AllBook>() {
            @Override
            public void onSuccess(AllBook allBook) {
                refreshing.set(false);

                hasNext = allBook.isHas_next();
                beanList.clear();
                beanList.addAll(allBook.getItems());
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
            }
        });
    }

    private void createViewModel() {
        if (beanList.size() <= 0) {
            return;
        }

        if (mBlankVM != null && mCurrentPage == 0) {
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
        }
        if (mCurrentPage == 0) {
            items.clear();
        }
        for (BookBean.ItemsBean.BooksBean childItem : beanList) {
            BookChildViewModel childViewModel = new BookChildViewModel(context);
            childViewModel.childName.set(childItem.getTitle());
            childViewModel.imgUrl.set(childItem.getThumb());
            childViewModel.link = childItem.getLink();
            items.add(childViewModel);
        }
    }
}
