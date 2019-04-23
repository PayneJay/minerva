package com.minerva.business.settings;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.minerva.base.BaseViewModel;
import com.minerva.business.settings.model.OptionsBean;
import com.minerva.business.settings.model.ReadSettingBean;

import java.util.ArrayList;
import java.util.List;

public class RecItemViewModel extends BaseViewModel {
    public ObservableField<String> categoryName = new ObservableField<>();
    public ObservableInt selectedPosition = new ObservableInt(0);
    public AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            value = options.get(position).getValue();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    public List<String> optionsText = new ArrayList<>();
    private List<OptionsBean> options = new ArrayList<>();
    private String id;
    private String value;

    RecItemViewModel(Context context, ReadSettingBean.RatiosBean ratio, List<OptionsBean> options) {
        super(context);
        categoryName.set(ratio.getName() + "：");
        setOptions(options);
        setId(ratio.getId());
        setSelectedPosition(ratio);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private void setOptions(List<OptionsBean> options) {
        this.options = options;
        for (OptionsBean opt : options) {
            optionsText.add(opt.getName());
        }
    }

    private void setSelectedPosition(ReadSettingBean.RatiosBean ratio) {
        for (int i = 0; i < options.size(); i++) {
            if (TextUtils.equals(options.get(i).getValue(), ratio.getValue())) {
                selectedPosition.set(i);
                value = ratio.getValue();
                break;
            }
        }
    }
}
