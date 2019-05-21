package com.minerva.business.article.comment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
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
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.utils.DisplayUtil;
import com.minerva.utils.ResourceUtil;

public class CommentItemViewModel extends BaseViewModel implements CommentOptViewModel.ICommentOperateListener {
    public ObservableField<String> headUrl = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> commentDate = new ObservableField<>();
    public ObservableField<String> commentText = new ObservableField<>();
    private PopupWindow operatePopup;
    private ICommentListener listener;
    private String id;

    CommentItemViewModel(Context context) {
        super(context);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setListener(ICommentListener listener) {
        this.listener = listener;
    }

    public void onItemLongClick() {
        showMenuPopup();
    }

    @Override
    public void onCopyClick() {
        if (operatePopup != null) {
            operatePopup.dismiss();
        }

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", commentText.get());
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }

        Toast.makeText(context, ResourceUtil.getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick() {
        if (operatePopup != null) {
            operatePopup.dismiss();
        }
        showConfirmDialog();
    }

    private void showMenuPopup() {
        if (operatePopup == null) {
            operatePopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtil.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            operatePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
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
        CommentOptViewModel fontViewModel = new CommentOptViewModel(context, this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_operate_comment_layout, null, false);
        binding.setVariable(BR.commentOptVM, fontViewModel);
        binding.executePendingBindings();
        operatePopup.setContentView(binding.getRoot());
        operatePopup.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(ResourceUtil.getString(R.string.dialog_title_note))
                .setMessage(ResourceUtil.getString(R.string.dialog_are_you_sure_delete))
                .setNegativeButton(ResourceUtil.getString(R.string.dialog_cancel), null)
                .setPositiveButton(ResourceUtil.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onDelete(id);
                        }
                    }
                });
        builder.show();
    }
}
