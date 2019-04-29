package com.minerva.business.site.menu;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.home.HomeActivity;
import com.minerva.business.site.imperfect.SiteFragment;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.DisplayUtils;
import com.minerva.widget.Loading;

public class MenuViewModel extends BaseViewModel implements IPopupMenuItemClickListener {
    protected IPopupMenuItemClickListener listener;
    protected Loading loading;
    private PopupWindow menuPopup;
    private String childId;
    private int groupId;

    MenuViewModel(Context context, IPopupMenuItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    String getChildId() {
        return childId == null ? "" : childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public void onMenuItemClick() {
        if (menuPopup != null) {
            menuPopup.dismiss();
        }
    }

    void showPopupMenu(String type) {
        if (menuPopup == null) {
            menuPopup = new PopupWindow(((HomeActivity) context).getWindow().getDecorView(), DisplayUtils.getScreenWidth() * 2 / 3, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            menuPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((HomeActivity) context).getWindow().getAttributes();
                    lp.alpha = 1f;
                    ((HomeActivity) context).getWindow().setAttributes(lp);
                }
            });
        }
        WindowManager.LayoutParams lp = ((HomeActivity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((HomeActivity) context).getWindow().setAttributes(lp);

        SiteViewModel viewModel = new SiteViewModel(context);
        viewModel.setGroupId(groupId);
        if (TextUtils.equals(type, SiteFragment.TYPE_CHILD)) {
            viewModel.setChildId(childId);
        }
        viewModel.createItemViewModel(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_sites_select_layout, null, false);
        binding.setVariable(BR.dialogSitesVM, viewModel);
        binding.executePendingBindings();
        menuPopup.setContentView(binding.getRoot());
        menuPopup.showAtLocation(((HomeActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

}
