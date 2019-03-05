package com.minerva.business.settings;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.FileUtils;
import com.minerva.utils.ResouceUtils;

import java.io.File;
import java.text.MessageFormat;

public class SettingsViewModel extends BaseViewModel {
    public ObservableField<String> clearCacheText = new ObservableField<>();
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    public SettingsViewModel(Context context) {
        super(context);

        try {
            File file = Constants.application.getCacheDir();
            clearCacheText.set(MessageFormat.format(ResouceUtils.getString(R.string.settings_clear_cache), FileUtils.getCacheSize(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCache() {
        FileUtils.clearInternalCache(context);
        clearCacheText.set(MessageFormat.format(ResouceUtils.getString(R.string.settings_clear_cache), "0kb"));
        Toast.makeText(context, ResouceUtils.getString(R.string.toast_cahce_clear_success), Toast.LENGTH_SHORT).show();
    }

    @BindingAdapter("navigationClickListener")
    public static void setNavigationClickListener(Toolbar toolbar, View.OnClickListener listener) {
        if (listener != null) {
            toolbar.setNavigationOnClickListener(listener);
        }
    }
}
