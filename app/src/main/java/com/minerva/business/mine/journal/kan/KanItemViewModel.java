package com.minerva.business.mine.journal.kan;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.journal.kan.model.FavKanModel;
import com.minerva.business.site.menu.IPopupMenuItemClickListener;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.ResourceUtils;
import com.minerva.widget.Loading;

import java.text.MessageFormat;

public class KanItemViewModel extends BaseViewModel {
    public ObservableField<String> kanName = new ObservableField<>();
    private IKanOperateListener listener;
    private Loading loading;
    String id;
    String targetId;

    KanItemViewModel(Context context, IKanOperateListener listener) {
        super(context);
        this.listener = listener;
    }

    public void onItemClick() {
        if (listener != null) {
            listener.onKanItemClick();
        }
        showConfirmDialog();
    }

    /**
     * 显示二次确认框
     */
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(ResourceUtils.getString(R.string.dialog_title_note))
                .setMessage(MessageFormat.format(ResourceUtils.getString(R.string.dialog_are_you_sure_migrate_kan), kanName.get()))
                .setNegativeButton(ResourceUtils.getString(R.string.dialog_cancel), null)
                .setPositiveButton(ResourceUtils.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        migrateKan();
                    }
                });
        builder.show();
    }

    /**
     * 执行迁移分组操作
     */
    private void migrateKan() {
        if (!CommonUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, ResourceUtils.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }
        FavKanModel.getInstance().migrateKan(id, targetId, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                if (listener != null) {
                    listener.onMigrateSuccess();
                }
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }
}
