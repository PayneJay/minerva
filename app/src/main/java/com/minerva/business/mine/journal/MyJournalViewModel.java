package com.minerva.business.mine.journal;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.list.ArticleListViewModel;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.business.mine.journal.model.JournalModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class MyJournalViewModel extends ArticleListViewModel implements CreateJournalViewModel.IDialogClickListener, JournalItemViewModel.IItemClickListener {
    public OnItemBind<BaseViewModel> journalItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.MY_JOURNAL_ITEM_TYPE:
                    itemBinding.set(BR.journalItemVM, R.layout.item_jounral_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    protected String catID;
    private boolean canEdit;
    private PopupWindow createPopup;
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.my_journal) {
                showCreateJournalDialog();
            }
            return true;
        }
    };

    protected MyJournalViewModel(Context context) {
        super(context, context.getClass().getSimpleName());
        requestServer();
    }

    public ObservableList<BaseViewModel> getItems() {
        return JournalModel.getInstance().getData();
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    @Override
    protected void requestServer() {
        if (!CommonUtils.isNetworkAvailable(context)) {
            setNetworkError();
            return;
        }

        JournalModel.getInstance().getKans(new NetworkObserver<KanBean>() {
            @Override
            public void onSuccess(KanBean kanBean) {
                refreshing.set(false);
                if (kanBean.getItems().size() <= 0) {
                    setEmptyPage();
                    return;
                }

                List<KanBean.ItemsBean> tempList = new ArrayList<>();
                tempList.addAll(kanBean.getItems());
                for (int i = 0; i < tempList.size(); i++) {
                    KanBean.ItemsBean item = tempList.get(i);
                    item.setSelected((i == 0));
                }
                JournalModel.getInstance().setKanList(tempList);
                JournalModel.getInstance().setData(getObserverList());
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
            }
        });
    }

    @Override
    public void confirm(String name, String desc, int type) {
        if (createPopup != null) {
            createPopup.dismiss();
        }

        JournalModel.getInstance().createJournal(name, desc, type, new NetworkObserver<KanBean>() {
            @Override
            public void onSuccess(KanBean kanBean) {
                requestServer();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void cancel() {
        if (createPopup != null && createPopup.isShowing()) {
            createPopup.dismiss();
        }
    }

    private ObservableList<BaseViewModel> getObserverList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();

        List<KanBean.ItemsBean> kanList = JournalModel.getInstance().getKanList();
        if (kanList.size() > 0) {
            for (KanBean.ItemsBean item : kanList) {
                JournalItemViewModel itemViewModel = new JournalItemViewModel(context, item);
                itemViewModel.canEdit.set(canEdit);
                if (itemViewModel.canEdit.get()) {
                    itemViewModel.setIsSelected(item.isSelected());
                }
                itemViewModel.setListener(this);
                temp.add(itemViewModel);
            }
        }
        return temp;
    }


    private void showCreateJournalDialog() {
        if (createPopup == null) {
            createPopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtils.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            createPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((BaseActivity) context).getWindow().setAttributes(lp);
                }
            });
        }
        WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((BaseActivity) context).getWindow().setAttributes(lp);

        CreateJournalViewModel viewModel = new CreateJournalViewModel(context);
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_create_journal_layout, null, false);
        binding.setVariable(BR.createJournalVM, viewModel);
        binding.executePendingBindings();
        createPopup.setContentView(binding.getRoot());
        createPopup.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    /**
     * 设置空页面
     */
    private void setEmptyPage() {
        refreshing.set(false);
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        items.clear();
        mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
        items.add(mBlankVM);
    }

    /**
     * 设置网络错误页
     */
    private void setNetworkError() {
        refreshing.set(false);
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        if (mCurrentPage == 0) {
            items.clear();
            mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
            items.add(mBlankVM);
        }
    }

    @Override
    public void onItemClick(String id) {
        catID = id;
        List<KanBean.ItemsBean> kanList = JournalModel.getInstance().getKanList();
        List<KanBean.ItemsBean> tempList = new ArrayList<>();
        for (KanBean.ItemsBean item : kanList) {
            item.setSelected(TextUtils.equals(id, item.getId()));
            tempList.add(item);
        }

        JournalModel.getInstance().setKanList(tempList);
        JournalModel.getInstance().setData(getObserverList());
    }
}
