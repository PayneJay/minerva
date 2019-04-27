package com.minerva.business.home.menu;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.utils.ResourceUtils;

public class CreateGroupViewModel extends BaseViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> titleContent = new ObservableField<>();
    private IDialogClickListener listener;

    public CreateGroupViewModel(Context context) {
        super(context);
    }

    public IDialogClickListener getListener() {
        return listener;
    }

    public void setListener(IDialogClickListener listener) {
        this.listener = listener;
    }

    public void onConfirmClick() {
        if (TextUtils.isEmpty(titleContent.get())) {
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_journal_char_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (listener != null) {
            listener.confirm(titleContent.get());
        }
    }

    public void onCancelClick() {
        if (listener != null) {
            listener.cancel();
        }
    }

    public interface IDialogClickListener {
        void confirm(String name);

        void cancel();
    }
}
