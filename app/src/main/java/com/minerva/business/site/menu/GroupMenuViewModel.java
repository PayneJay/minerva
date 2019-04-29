package com.minerva.business.site.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.business.home.HomeActivity;
import com.minerva.business.site.menu.model.MenuModel;
import com.minerva.business.site.imperfect.SiteFragment;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.DisplayUtils;
import com.minerva.utils.ResourceUtils;
import com.minerva.widget.Loading;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;

public class GroupMenuViewModel extends MenuViewModel implements CreateGroupViewModel.IDialogClickListener {
    public ObservableBoolean isEditMenuGone = new ObservableBoolean(false);
    private PopupWindow renamePopup;
    private String name;

    public GroupMenuViewModel(Context context, IPopupMenuItemClickListener listener) {
        super(context, listener);
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void onEditClick() {
        if (listener != null) {
            listener.onMenuItemClick();
        }

        showRenameDialog();
    }

    public void onTransferClick() {
        if (listener != null) {
            listener.onMenuItemClick();
        }

        showPopupMenu(SiteFragment.TYPE_GROUP);
    }

    public void onDeleteClick() {
        if (listener != null) {
            listener.onMenuItemClick();
        }

        showConfirmDialog();
    }

    public void onMarkAllClick() {
        if (listener != null) {
            listener.onMenuItemClick();
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        MenuModel.getInstance().markJuheRead(getGroupId(), new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                EventBus.getDefault().postSticky(baseBean);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });

    }

    @Override
    public void confirm(String name) {
        if (renamePopup != null) {
            renamePopup.dismiss();
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        MenuModel.getInstance().renameGroup(getGroupId(), name, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_update_group_success), Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(baseBean);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    @Override
    public void cancel() {
        if (renamePopup != null) {
            renamePopup.dismiss();
        }
    }

    private void showRenameDialog() {
        if (renamePopup == null) {
            renamePopup = new PopupWindow(((HomeActivity) context).getWindow().getDecorView(), DisplayUtils.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            renamePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((HomeActivity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((HomeActivity) context).getWindow().setAttributes(lp);
                }
            });
        }
        WindowManager.LayoutParams lp = ((HomeActivity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((HomeActivity) context).getWindow().setAttributes(lp);

        CreateGroupViewModel viewModel = new CreateGroupViewModel(context);
        viewModel.title.set(ResourceUtils.getString(R.string.dialog_rename_group));
        viewModel.titleContent.set(name);
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_create_group_layout, null, false);
        binding.setVariable(BR.createGroupVM, viewModel);
        binding.executePendingBindings();
        renamePopup.setContentView(binding.getRoot());
        renamePopup.showAtLocation(((HomeActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(ResourceUtils.getString(R.string.dialog_title_note))
                .setMessage(MessageFormat.format(ResourceUtils.getString(R.string.dialog_are_you_sure_delete), name))
                .setNegativeButton(ResourceUtils.getString(R.string.dialog_cancel), null)
                .setPositiveButton(ResourceUtils.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteGroup();
                    }
                });
        builder.show();
    }

    private void deleteGroup() {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        MenuModel.getInstance().removeGroup(getGroupId(), new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                EventBus.getDefault().postSticky(baseBean);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }
}
