package com.minerva.business.mine.journal;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.utils.ResourceUtil;

public class CreateJournalViewModel extends BaseViewModel {
    public ObservableField<String> titleText = new ObservableField<>();
    public ObservableField<String> titleContent = new ObservableField<>();
    public ObservableField<String> desContent = new ObservableField<>();
    public ObservableBoolean onlyShowSelf = new ObservableBoolean();
    public CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            type = isChecked ? 0 : 1;
        }
    };
    private IDialogClickListener listener;
    private int type = 1;

    public CreateJournalViewModel(Context context) {
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
            Toast.makeText(context, ResourceUtil.getString(R.string.toast_journal_char_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (listener != null) {
            listener.confirm(titleContent.get(), desContent.get(), type);
        }
    }

    public void onCancelClick() {
        if (listener != null) {
            listener.cancel();
        }
    }

    public interface IDialogClickListener {
        void confirm(String name, String desc, int type);

        void cancel();
    }
}
