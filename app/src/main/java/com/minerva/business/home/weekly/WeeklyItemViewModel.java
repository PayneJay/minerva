package com.minerva.business.home.weekly;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.home.weekly.model.WeekListBean;
import com.minerva.common.Constants;
import com.minerva.utils.DateUtils;

import java.util.Date;
import java.util.Random;

public class WeeklyItemViewModel extends BaseViewModel {
    public ObservableField<String> dateText = new ObservableField<>();
    public ObservableField<String> weekText = new ObservableField<>();
    public ObservableInt colorRes = new ObservableInt(Color.RED);
    private String id;
    private long time;
    private int[] colors = {R.color.color_FF0000, R.color.colorPrimary, R.color.color_6F00D2,
            R.color.color_1E90FF, R.color.color_FF77FF, R.color.color_548B54,
            R.color.color_8B658B, R.color.color_3FE280, R.color.colorAccent};

    WeeklyItemViewModel(Context context, WeekListBean.ItemsBean item) {
        super(context);
        id = item.getId();
        time = item.getTime();
        initView(item);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void onItemClick() {
        Intent intent = new Intent(context, WeeklyDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.WEEKLY_ID, id);
        intent.putExtra(Constants.KeyExtra.WEEKLY_DATE, time);
        context.startActivity(intent);
    }

    private void initView(WeekListBean.ItemsBean item) {
        dateText.set(DateUtils.date2Str(new Date(item.getTime()), "MM/dd"));
        weekText.set(DateUtils.getWeek(new Date(item.getTime())));
        Random random = new Random();
        int nextInt = random.nextInt(colors.length);
        colorRes.set(colors[nextInt]);
    }
}
