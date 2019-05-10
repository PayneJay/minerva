package com.minerva.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;

public class Loading extends Dialog {
    public Loading(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Loading dialog;
        private View layout;
        private LoadingViewModel loadingViewModel;
        private String mMessage = "";
        private int iconID;

        public Builder(Context context) {
            dialog = new Loading(context, R.style.Loading);
            loadingViewModel = new LoadingViewModel(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewDataBinding bind = DataBindingUtil.inflate(inflater, R.layout.loading_layout, null, false);
            bind.setVariable(BR.loadingVm, loadingViewModel);
            bind.executePendingBindings();
            layout = bind.getRoot();
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        public Builder setMessage(String message) {
            this.mMessage = message;
            loadingViewModel.setLoadingMessage(this.mMessage);
            return this;
        }

        public Builder setIcon(int resID) {
            this.iconID = resID;
            loadingViewModel.setImage(this.iconID);
            return this;
        }

        public Loading show() {
            create();
            dialog.show();
            return dialog;
        }

        public void dismiss() {
            dialog.dismiss();
        }

        private void create() {
            dialog.setContentView(layout);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    public static class LoadingViewModel extends BaseViewModel {
        public ObservableField<String> message = new ObservableField<>();
        public ObservableInt loadingImage = new ObservableInt(0);
        public ObservableBoolean messageIsGone = new ObservableBoolean(true);

        public LoadingViewModel(Context context) {
            super(context);
        }

        public void setLoadingMessage(String str) {
            if (TextUtils.isEmpty(str)) {
                messageIsGone.set(true);
            } else {
                messageIsGone.set(false);
            }
            this.message.set(str);
        }

        public String getLoadingMessage() {
            return this.message.get();
        }

        public void setImage(int resID) {
            this.loadingImage.set(resID);
        }

        public int getImage() {
            return this.loadingImage.get();
        }
    }
}
