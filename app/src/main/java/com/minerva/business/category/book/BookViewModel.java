package com.minerva.business.category.book;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.book.model.BookModel;
import com.minerva.business.category.mag.SpecialGroupViewModel;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.SpecialModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.RefreshListViewModel;
import com.minerva.common.Constants;
import com.minerva.common.IPageStateListener;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class BookViewModel extends RefreshListViewModel implements IPageStateListener {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public OnItemBind<BaseViewModel> bookItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.SPECIAL_GROUP_TYPE:
                    itemBinding.set(BR.specialGroupVM, R.layout.item_special_group_layout);
                    break;
                case Constants.RecyclerItemType.SPECIAL_CHILD_TYPE:
                    itemBinding.set(BR.bookChildVM, R.layout.item_book_child_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    public GridLayoutManager glManager;
    private BlankViewModel mBlankVM;
    private List<BookBean.ItemsBean> beanXList = new ArrayList<>();

    BookViewModel(Context context) {
        super(context);
        requestServer();
    }

    @Override
    protected void requestServer() {
        refreshing.set(true);
        if (!CommonUtil.isNetworkAvailable(context)) {
            refreshing.set(false);
            setPageByState(Constants.PageStatus.NETWORK_EXCEPTION);
            return;
        }
        SpecialModel.getInstance().getBookList(new NetworkObserver<BookBean>() {
            @Override
            public void onSuccess(BookBean bookBean) {
                refreshing.set(false);
                beanXList.clear();
                beanXList.addAll(bookBean.getItems());
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
                createViewModel();
            }
        });
    }

    public ObservableList<BaseViewModel> getItems() {
        return BookModel.getInstance().getData();
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestServer();
        }
    };

    @Override
    public void setPageByState(int state) {
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        mBlankVM.setStatus(state);
        BookModel.getInstance().clear();
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        temp.add(mBlankVM);
        BookModel.getInstance().setData(temp);
    }

    void setGirdLayoutManager() {
        glManager = new GridLayoutManager(context, 3);
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
    }

    @Override
    protected void createViewModel() {
        if (beanXList.size() <= 0) {
            setPageByState(Constants.PageStatus.NO_DATA);
            return;
        }

        BookModel.getInstance().setData(getObservableList());
    }

    private ObservableList<BaseViewModel> getObservableList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        for (BookBean.ItemsBean groupItem : beanXList) {
            SpecialGroupViewModel groupViewModel = new SpecialGroupViewModel(context);
            groupViewModel.groupName.set(groupItem.getTagName());
            groupViewModel.type = groupItem.getTagId();
            groupViewModel.tabType = Constants.CategoryTabType.TAB_BOOK;
            groupViewModel.setId(groupItem.getTagId() + "");
            temp.add(groupViewModel);

            List<BookBean.ItemsBean.BooksBean> beanList = groupItem.getBooks();
            if (beanList.size() > 0) {
                for (BookBean.ItemsBean.BooksBean childItem : beanList) {
                    BookChildViewModel childViewModel = new BookChildViewModel(context);
                    childViewModel.childName.set(childItem.getTitle());
                    childViewModel.imgUrl.set(childItem.getThumb());
                    childViewModel.link = childItem.getLink();
                    childViewModel.setId(childItem.getId());
                    temp.add(childViewModel);
                }
            }
        }
        return temp;
    }

}

