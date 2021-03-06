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
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.ToastUtil;
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
                .setTitle(ResourceUtil.getString(R.string.dialog_title_note))
                .setMessage(MessageFormat.format(ResourceUtil.getString(R.string.dialog_are_you_sure_migrate_kan), kanName.get()))
                .setNegativeButton(ResourceUtil.getString(R.string.dialog_cancel), null)
                .setPositiveButton(ResourceUtil.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
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
        if (!CommonUtil.isNetworkAvailable(context)) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.network_error));
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
