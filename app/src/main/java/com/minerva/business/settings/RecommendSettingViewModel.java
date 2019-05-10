package com.minerva.business.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.settings.model.OptionsBean;
import com.minerva.business.settings.model.ReadSettingBean;
import com.minerva.business.settings.model.SettingModel;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResourceUtils;
import com.minerva.widget.Loading;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class RecommendSettingViewModel extends BaseViewModel {
    public OnItemBind<BaseViewModel> selectItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            if (item instanceof RecItemViewModel) {
                itemBinding.set(BR.recItemVM, R.layout.item_recommend_select_layout);
            }
        }
    };
    private List<ReadSettingBean.RatiosBean> mRatios = new ArrayList<>();
    private List<OptionsBean> mOptions = new ArrayList<>();
    private Loading loading;

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    RecommendSettingViewModel(Context context) {
        super(context);
        requestServer();
    }

    public ObservableList<BaseViewModel> getItems() {
        return SettingModel.getInstance().getData();
    }

    public void submit() {
        DiffObservableList<BaseViewModel> data = SettingModel.getInstance().getData();
        String tech = "0.7", design = "0.7", guru = "0.7";
        for (BaseViewModel viewModel : data) {
            if (viewModel instanceof RecItemViewModel) {
                if (TextUtils.equals(((RecItemViewModel) viewModel).getId(), "1")) {
                    tech = ((RecItemViewModel) viewModel).getValue();
                } else if (TextUtils.equals(((RecItemViewModel) viewModel).getId(), "2")) {
                    design = ((RecItemViewModel) viewModel).getValue();
                } else if (TextUtils.equals(((RecItemViewModel) viewModel).getId(), "3")) {
                    guru = ((RecItemViewModel) viewModel).getValue();
                }
            }
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        }
        SettingModel.getInstance().updateRead(tech, design, guru, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                showDialog();
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
    }

    private void requestServer() {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        }

        SettingModel.getInstance().getReadSetting(new NetworkObserver<ReadSettingBean>() {
            @Override
            public void onSuccess(ReadSettingBean readSettingBean) {
                loading.dismiss();
                List<ReadSettingBean.RatiosBean> ratios = readSettingBean.getRatios();
                List<List<String>> optionList = readSettingBean.getOptions();
                setOptions(ratios, optionList);
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    private void setOptions(List<ReadSettingBean.RatiosBean> ratios, List<List<String>> optionList) {
        mRatios.clear();
        mRatios.addAll(ratios);
        mOptions.clear();

        for (List<String> list : optionList) {
            if (list != null) {
                mOptions.add(new OptionsBean(list.get(1), list.get(0)));
            }
        }
    }

    private void createViewModel() {
        SettingModel.getInstance().setData(getObserverList());
    }

    private ObservableList<BaseViewModel> getObserverList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        for (ReadSettingBean.RatiosBean ratio : mRatios) {
            temp.add(new RecItemViewModel(context, ratio, mOptions));
        }
        return temp;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(ResourceUtils.getString(R.string.dialog_title_note))
                .setMessage(ResourceUtils.getString(R.string.dialog_read_update_success))
                .setPositiveButton(ResourceUtils.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}
