package com.minerva.business.mine.journal;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

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

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class MyJournalViewModel extends ArticleListViewModel implements CreateJournalViewModel.IDialogClickListener {
    public OnItemBind<BaseViewModel> journalItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.MY_JOURNAL_ITEM_TYPE:
                    itemBinding.set(BR.jounralItemVM, R.layout.item_jounral_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    private PopupWindow helpDialog;
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

    MyJournalViewModel(Context context) {
        super(context, context.getClass().getSimpleName());
        requestServer();
    }

    public ObservableList<BaseViewModel> getItems() {
        return JournalModel.getInstance().getData();
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

                JournalModel.getInstance().setKanList(kanBean.getItems());
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
        if (helpDialog != null) {
            helpDialog.dismiss();
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
        if (helpDialog != null && helpDialog.isShowing()) {
            helpDialog.dismiss();
        }
    }

    private ObservableList<BaseViewModel> getObserverList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();

        List<KanBean.ItemsBean> kanList = JournalModel.getInstance().getKanList();
        if (kanList.size() > 0) {
            for (KanBean.ItemsBean item : kanList) {
                JournalItemViewModel viewModel = new JournalItemViewModel(context);
                viewModel.journalName = item.getName();
                viewModel.articleCount = item.getAc() + "";
                viewModel.setId(item.getId());
                temp.add(viewModel);
            }
        }
        return temp;
    }


    private void showCreateJournalDialog() {
        if (helpDialog == null) {
            helpDialog = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), CommonUtils.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            helpDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((BaseActivity) context).getWindow().setAttributes(lp);
                }
            });
        }
        WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
        lp.alpha = 0.8f;
        ((BaseActivity) context).getWindow().setAttributes(lp);

        CreateJournalViewModel viewModel = new CreateJournalViewModel(context);
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_create_journal_layout, null, false);
        binding.setVariable(BR.createJournalVM, viewModel);
        binding.executePendingBindings();
        helpDialog.setContentView(binding.getRoot());
        helpDialog.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
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
}
