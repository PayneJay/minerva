package com.minerva.business.mine.journal.kan;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.article.list.ArticleListViewModel;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.business.mine.journal.CreateJournalViewModel;
import com.minerva.business.mine.journal.kan.model.FavKanBean;
import com.minerva.business.mine.journal.kan.model.FavKanModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.common.IPageStateListener;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.DisplayUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.widget.Loading;

import java.util.ArrayList;
import java.util.List;

public class FavKanViewModel extends ArticleListViewModel implements IPageStateListener, PopupMenu.OnMenuItemClickListener, CreateJournalViewModel.IDialogClickListener, IKanOperateListener {
    private String kanId;
    private List<ArticleDetailBean.ArticleBean> articleList = new ArrayList<>();
    public ObservableField<String> titleText = new ObservableField<>("");
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.siteMore) {
                showPopupMenu();
            }
            return true;
        }
    };
    private Loading loading;
    private PopupWindow editKanPopup; //编辑推刊信息框
    private PopupWindow moveKanPopup; //迁移推刊分组框
    private String kanDesc; //期刊描述
    private boolean onlyShowSelf; //仅自己可见

    FavKanViewModel(Context context, String className) {
        super(context, className);
        kanId = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.FAV_KAN_ID);

        refreshing.set(true);
        requestServer();
    }

    public ObservableList<BaseViewModel> getItems() {
        return FavKanModel.getInstance().getData();
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

        /*
         * 根据推刊id获取文章列表
         */
        FavKanModel.getInstance().getFavKansById(kanId, new NetworkObserver<FavKanBean>() {
            @Override
            public void onSuccess(FavKanBean favKanBean) {
                refreshing.set(false);
                KanBean.ItemsBean kan = favKanBean.getKan();
                if (kan != null) {
                    titleText.set(kan.getName());
                    kanDesc = kan.getDesc();
                    onlyShowSelf = kan.getType() == 0;
                }
                hasNext = favKanBean.isHas_next();
                articleList.clear();
                articleList.addAll(favKanBean.getArticles());
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
            FavKanModel.getInstance().clear();
            if (articleList.size() <= 0) {
                setPageByState(Constants.PageStatus.NO_DATA);
                return;
            }
        }

        FavKanModel.getInstance().setData(getObservableList());
    }

    @Override
    public void setPageByState(int state) {
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        mBlankVM.setStatus(state);
        FavKanModel.getInstance().clear();
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        temp.add(mBlankVM);
        FavKanModel.getInstance().setData(temp);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_edit_group_name) {
            showEditPopup();
            return true;
        }
        if (item.getItemId() == R.id.menu_move_kan) {
            showMigratePopup();
            return true;
        }
        if (item.getItemId() == R.id.menu_delete_kan) {
            showDeleteDialog();
            return true;
        }
        return false;
    }

    @Override
    public void confirm(String name, String desc, int type) {
        if (editKanPopup != null && editKanPopup.isShowing()) {
            editKanPopup.dismiss();
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }
        FavKanModel.getInstance().updateKan(kanId, name, desc, type, new NetworkObserver<KanBean>() {
            @Override
            public void onSuccess(KanBean kanBean) {
                loading.dismiss();
                refreshing.set(true);
                requestServer();
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_update_group_success), Toast.LENGTH_SHORT).show();
                for (KanBean.ItemsBean item : kanBean.getItems()) {
                    if (TextUtils.equals(item.getId(), kanId)) {
                        titleText.set(item.getName());
                        break;
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    @Override
    public void cancel() {
        if (editKanPopup != null && editKanPopup.isShowing()) {
            editKanPopup.dismiss();
        }
    }

    @Override
    public void onKanItemClick() {
        if (moveKanPopup != null && moveKanPopup.isShowing()) {
            moveKanPopup.dismiss();
        }
    }

    @Override
    public void onMigrateSuccess() {
        mCurrentPage = 0;
        refreshing.set(true);
        requestServer();
    }

    /**
     * 显示编辑框
     */
    private void showEditPopup() {
        if (editKanPopup == null) {
            editKanPopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtil.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            editKanPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
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
        viewModel.titleText.set(ResourceUtil.getString(R.string.dialog_edit_journal));
        viewModel.titleContent.set(titleText.get());
        viewModel.desContent.set(kanDesc);
        viewModel.onlyShowSelf.set(onlyShowSelf);
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_create_journal_layout, null, false);
        binding.setVariable(BR.createJournalVM, viewModel);
        binding.executePendingBindings();
        editKanPopup.setContentView(binding.getRoot());
        editKanPopup.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 显示迁移框
     */
    private void showMigratePopup() {
        if (moveKanPopup == null) {
            moveKanPopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtil.getScreenWidth() * 2 / 3, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            moveKanPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
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

        MigrateKanViewModel viewModel = new MigrateKanViewModel(context);
        viewModel.createItemViewModel(kanId, this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_migrate_kan_layout, null, false);
        binding.setVariable(BR.migrateKanVM, viewModel);
        binding.executePendingBindings();
        moveKanPopup.setContentView(binding.getRoot());
        moveKanPopup.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 显示删除框
     */
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(ResourceUtil.getString(R.string.dialog_title_note))
                .setMessage(ResourceUtil.getString(R.string.dialog_are_you_sure_delete_kan))
                .setNegativeButton(ResourceUtil.getString(R.string.dialog_cancel), null)
                .setPositiveButton(ResourceUtil.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteKanById();
                    }
                });
        builder.show();
    }

    /**
     * 通过id删除推刊
     */
    private void deleteKanById() {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        FavKanModel.getInstance().deleteKanById(kanId, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                ((BaseActivity) context).finish();
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    private ObservableList<BaseViewModel> getObservableList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        temp.addAll(getItems());
        for (ArticleDetailBean.ArticleBean article : articleList) {
            ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
            viewModel.content.set(article.getTitle());
            viewModel.date.set(article.getFeed_title() + "  " + article.getRectime());
            viewModel.imgUrl.set(article.getImg());
            viewModel.isHotFlagGone.set(article.getSt() != 2);
            viewModel.articleID = article.getId();
            temp.add(viewModel);
        }
        return temp;
    }

    /**
     * 显示菜单项
     */
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(context, ((BaseActivity) context).getWindow().getDecorView().getRootView().findViewById(R.id.fav_kan_toolbar));
        if (FavKanModel.getInstance().getItemsFilterBlank().size() > 0) {
            popupMenu.getMenuInflater().inflate(R.menu.kan_popup_menu, popupMenu.getMenu());
        } else {
            popupMenu.getMenuInflater().inflate(R.menu.kan_popup_without_migrate_menu, popupMenu.getMenu());
        }
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }
}
