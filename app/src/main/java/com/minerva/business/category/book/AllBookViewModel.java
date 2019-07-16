package com.minerva.business.category.book;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.book.model.AllBook;
import com.minerva.business.category.book.model.AllBookModel;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.SpecialModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.viewmodel.RefreshListViewModel;
import com.minerva.common.Constants;
import com.minerva.common.IPageStateListener;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class AllBookViewModel extends RefreshListViewModel implements IPageStateListener {
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
        refreshing.set(true);
        requestServer();
    }

    public ObservableList<BaseViewModel> getItems() {
        return AllBookModel.getInstance().getData();
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mCurrentPage = 0;
            refreshing.set(true);
            requestServer();
        }
    };

    public void loadMore() {
        if (hasNext) {
            mCurrentPage++;
            refreshing.set(true);
            requestServer();
        }
    }

    public GridLayoutManager getGlManager() {
        GridLayoutManager glManager = new GridLayoutManager(context, 3);
        glManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                try {
                    if (getItems().get(position).getViewType() != Constants.RecyclerItemType.SPECIAL_CHILD_TYPE) {
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

    @Override
    public void setPageByState(int state) {
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        mBlankVM.setStatus(state);
        AllBookModel.getInstance().clear();
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        temp.add(mBlankVM);
        AllBookModel.getInstance().setData(temp);
    }

    @Override
    protected void requestServer() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            refreshing.set(false);
            if (mCurrentPage == 0) {
                setPageByState(Constants.PageStatus.NETWORK_EXCEPTION);
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
                createViewModel();
            }
        });
    }

    @Override
    protected void createViewModel() {
        if (mCurrentPage == 0) {
            AllBookModel.getInstance().clear();
            if (beanList.size() <= 0) {
                setPageByState(Constants.PageStatus.NO_DATA);
                return;
            }
        }

        AllBookModel.getInstance().setData(getObservableList());
    }

    private ObservableList<BaseViewModel> getObservableList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        temp.addAll(getItems());
        for (BookBean.ItemsBean.BooksBean childItem : beanList) {
            BookChildViewModel childViewModel = new BookChildViewModel(context);
            childViewModel.childName.set(childItem.getTitle());
            childViewModel.imgUrl.set(childItem.getThumb());
            childViewModel.link = childItem.getLink();
            childViewModel.setId(childItem.getId());
            temp.add(childViewModel);
        }
        return temp;
    }
}
