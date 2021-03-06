package com.minerva.business.article.comment;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.comment.model.CommentDetail;
import com.minerva.business.article.comment.model.CommentListBean;
import com.minerva.business.article.comment.model.CommentModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.ToastUtil;
import com.minerva.widget.Loading;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class CommentViewModel extends BaseViewModel implements ICommentListener {
    public OnItemBind<BaseViewModel> commentItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            if (item instanceof CommentItemViewModel) {
                itemBinding.set(BR.commentItemVM, R.layout.item_comment_layout);
            }
            if (item instanceof BlankViewModel) {
                itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
            }
        }
    };
    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    private Loading loading;
    private BlankViewModel mBlankVM;
    private String aid;
    private List<CommentDetail.CommentBean> comments = new ArrayList<>();
    private Dialog commentDialog;
    private int mPage;
    private boolean hasNext, isLoadMore;

    CommentViewModel(Context context) {
        super(context);
        aid = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.ARTICLE_ID);
        requestServer();
    }

    public ObservableList<BaseViewModel> getItems() {
        return CommentModel.getInstance().getData();
    }

    public void loadMore() {
        isLoadMore = true;
        if (hasNext) {
            requestServer();
        }
    }

    public void onFloatingActionClick() {
        if (GlobalData.getInstance().isLogin()) {
            showCommentInputDialog();
        } else {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.toast_please_login_first));
        }
    }

    @Override
    public void onDelete(String id) {
        if (checkNetwork()) return;

        CommentModel.getInstance().deleteCommentObservable(id)
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseBean, ObservableSource<CommentListBean>>() {
                    @Override
                    public ObservableSource<CommentListBean> apply(BaseBean baseBean) {
                        return CommentModel.getInstance().getCommentsObservable(aid, 0);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<CommentListBean>() {
                    @Override
                    public void onSuccess(CommentListBean commentListBean) {
                        loading.dismiss();
                        handleData(commentListBean);
                        createViewModel();
                    }

                    @Override
                    public void onFailure(String msg) {
                        loading.dismiss();
                    }
                });
    }

    @Override
    public void onBackClick() {
        if (commentDialog != null) {
            commentDialog.dismiss();
        }
    }

    @Override
    public void onCommentSubmit(String comment) {
        if (TextUtils.isEmpty(comment)) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.please_check_your_content_empty));
            return;
        }

        if (checkNetwork()) return;

        CommentModel.getInstance().submitComment(aid, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<CommentDetail>() {
                    @Override
                    public void accept(CommentDetail commentDetail) {
                        if (!TextUtils.isEmpty(commentDetail.getError())) {
                            ToastUtil.showMsg(commentDetail.getError());
                        }
                    }
                })
                .flatMap(new Function<CommentDetail, ObservableSource<CommentListBean>>() {
                    @Override
                    public ObservableSource<CommentListBean> apply(CommentDetail baseBean) {
                        return CommentModel.getInstance().getCommentsObservable(aid, 0);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<CommentListBean>() {
                    @Override
                    public void onSuccess(CommentListBean commentListBean) {
                        if (commentDialog != null) {
                            commentDialog.dismiss();
                        }
                        loading.dismiss();
                        handleData(commentListBean);
                        createViewModel();
                    }

                    @Override
                    public void onFailure(String msg) {
                        loading.dismiss();
                    }
                });
    }

    private void handleData(CommentListBean commentListBean) {
        hasNext = commentListBean.isHas_next();
        comments.clear();
        comments.addAll(commentListBean.getComments());
    }

    private boolean checkNetwork() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.network_error));
            return true;
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }
        return false;
    }

    private void requestServer() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            if (isLoadMore) {
                ToastUtil.showMsg(ResourceUtil.getString(R.string.network_error));
            } else {
                setErrorPage();
            }
            return;
        }

        if (!isLoadMore) {
            if (loading == null) {
                loading = new Loading.Builder(context).show();
            } else {
                loading.show();
            }
        }
        CommentModel.getInstance().getCommentsByAid(aid, mPage, new NetworkObserver<CommentListBean>() {
            @Override
            public void onSuccess(CommentListBean commentListBean) {
                loading.dismiss();
                handleData(commentListBean);
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    private void createViewModel() {
        if (mPage == 0) {
            CommentModel.getInstance().clear();
        }
        CommentModel.getInstance().setData(getObserverList());
    }

    private ObservableList<BaseViewModel> getObserverList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        if (comments.size() > 0) {
            for (CommentDetail.CommentBean comment : comments) {
                if (comment != null) {
                    CommentItemViewModel itemViewModel = new CommentItemViewModel(context);
                    itemViewModel.headUrl.set(comment.getAvatar());
                    itemViewModel.userName.set(comment.getUsername());
                    itemViewModel.commentDate.set((comment.getTimestamp()));
                    itemViewModel.commentText.set(comment.getContent());
                    itemViewModel.setId(comment.getId());
                    itemViewModel.setListener(this);
                    temp.add(itemViewModel);
                }
            }
        } else {
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
            temp.add(mBlankVM);
        }
        return temp;
    }

    private void setErrorPage() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        CommentModel.getInstance().clear();
        mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
        temp.add(mBlankVM);
        CommentModel.getInstance().setData(temp);
    }

    private void showCommentInputDialog() {
        if (commentDialog == null) {
            commentDialog = new Dialog(context, R.style.SlideBottomDialogStyle);
        }

        CommentReportViewModel viewModel = new CommentReportViewModel(context, this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_report_comment_layout, null, false);
        binding.setVariable(BR.commentReportVM, viewModel);
        binding.executePendingBindings();
        commentDialog.setContentView(binding.getRoot());
        commentDialog.show();
    }
}
