package com.minerva.business.article.detail;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.comment.CommentActivity;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.detail.model.ArticleDetailModel;
import com.minerva.business.mine.collection.model.CollectionModel;
import com.minerva.business.mine.collection.model.UnFavBean;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.common.GlobalData;
import com.minerva.common.WebViewActivity;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.DisplayUtils;
import com.minerva.utils.ResourceUtils;
import com.minerva.widget.Loading;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class ArticleDetailViewModel extends BaseViewModel implements UMShareListener, PopupMenu.OnMenuItemClickListener, ISelectJournalListener, IFontSelectedListener {
    public ObservableField<String> articleContent = new ObservableField<>("");
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public ObservableInt titleTextSize = new ObservableInt(20);
    public ObservableInt dateTextSize = new ObservableInt(14);
    public ObservableInt contentTextSize = new ObservableInt(18);
    private ArticleDetailBean.ArticleBean article;
    private ShareAction mShareAction;
    private String articleID;
    private String mArticleLink;
    private String mMarkReadOrNotText, mCollectionOrNotText;
    private Dialog journalListDialog;
    private Loading loading;
    private PopupWindow fontPopup;
    private boolean isFav; //表示是否收藏过(1已收藏/0未收藏)
    private boolean smallSelected = false, middleSelected = true, bigSelected = false;

    ArticleDetailViewModel(Context context) {
        super(context);
        articleID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.ARTICLE_ID);
        getArticleDetail(articleID);
    }

    private void getArticleDetail(String articleID) {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        }
        ArticleDetailModel.getInstance().getArticleDetail(articleID, new NetworkObserver<ArticleDetailBean>() {
            @Override
            public void onSuccess(ArticleDetailBean articleDetailBean) {
                loading.dismiss();
                article = articleDetailBean.getArticle();
                setFavorite(articleDetailBean.getLike());
                if (article != null) {
                    articleContent.set(article.getContent());
                    title.set(article.getTitle());
                    date.set(article.getFeed_title() + "   " + article.getTime());
                    mArticleLink = article.getUrl();

                    ArticleDetailModel.getInstance().addArticleWithKey(context, article, Constants.KeyExtra.READ_HISTORY_MAP);
                }
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    /**
     * 设置是否是已收藏
     *
     * @param like 1收藏/0未收藏
     */
    private void setFavorite(String like) {
        isFav = TextUtils.equals(like, "1");
    }

    public void share() {
        mShareAction = new ShareAction((BaseActivity) context);
        UMWeb web = new UMWeb(Constants.shareBaseUrl + articleID);
        if (article != null) {
            UMImage thumb;
            if (TextUtils.isEmpty(article.getImg())) {
                thumb = new UMImage(context, R.mipmap.icon_launcher);
            } else {
                thumb = new UMImage(context, article.getImg());
            }
            web.setTitle(article.getFeed_title());//标题
            web.setThumb(thumb);  //缩略图
            web.setDescription(article.getTitle());//描述
        }

        mShareAction.withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .setCallback(this).open();
    }

    public void comment() {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(Constants.KeyExtra.ARTICLE_ID, articleID);
        context.startActivity(intent);
    }

    public void more() {
        final Map<String, Object> readLater = ArticleDetailModel.getInstance().getArticlesByKey(context, Constants.KeyExtra.READ_LATER_MAP);
        if (readLater.keySet().contains(articleID)) {
            //已添加待读，该操作为取消待读
            mMarkReadOrNotText = ResourceUtils.getString(R.string.toolbar_menu_cancel_read);
        } else {
            //未添加待读，执行添加操作
            mMarkReadOrNotText = ResourceUtils.getString(R.string.toolbar_menu_add_read);
        }

        //判断是否收藏
        if (isFav) {
            mCollectionOrNotText = ResourceUtils.getString(R.string.toolbar_menu_cancel_collection);
        } else {
            mCollectionOrNotText = ResourceUtils.getString(R.string.toolbar_menu_add_collection);
        }
        showPopupMenu();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Toast.makeText(context, "分享成功了……", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.i("share", throwable.getMessage(), throwable);
        Toast.makeText(context, "分享失败了……" + throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Toast.makeText(context, "分享取消了……", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_read:
                markOrCancelRead();
                break;
            case R.id.menu_add_collection:
                goMyCollection();
                break;
            case R.id.menu_view_original:
                viewOriginal();
                break;
            case R.id.menu_copy_link:
                copyLink();
                break;
            case R.id.menu_change_font_size:
                changeFontSize();
                break;
            case R.id.menu_open_turn_over:
                Constants.showToast(context);
                break;
        }
        return true;
    }

    private void changeFontSize() {
        if (fontPopup == null) {
            fontPopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtils.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            fontPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
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
        FontViewModel fontViewModel = new FontViewModel(context, this);
        fontViewModel.isSmallSelected.set(smallSelected);
        fontViewModel.isMiddleSelected.set(middleSelected);
        fontViewModel.isBigSelected.set(bigSelected);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_font_list_layout, null, false);
        binding.setVariable(BR.fontVM, fontViewModel);
        binding.executePendingBindings();
        fontPopup.setContentView(binding.getRoot());
        fontPopup.showAtLocation(((BaseActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void goMyCollection() {
        if (!GlobalData.getInstance().isLogin()) {
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
            return;
        }

        if (isFav) {
            cancelFavorite();
        } else {
            showAddJournalDialog();
        }
    }

    private void viewOriginal() {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.KeyExtra.BOOK_JD_LINK, mArticleLink);
        context.startActivity(intent);
    }

    private void copyLink() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mArticleLink);
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }

        Toast.makeText(context, ResourceUtils.getString(R.string.toast_copy_clipboard), Toast.LENGTH_SHORT).show();
    }

    private void markOrCancelRead() {
        final Map<String, Object> readLater = ArticleDetailModel.getInstance().getArticlesByKey(context, Constants.KeyExtra.READ_LATER_MAP);
        if (readLater.keySet().contains(articleID)) {
            //已添加待读，该操作为取消待读
            cancelRead();
        } else {
            //未添加待读，执行添加操作
            markRead();
        }
    }

    private void markRead() {
        if (!GlobalData.getInstance().isLogin()) {
            ArticleDetailModel.getInstance().addArticleWithKey(context, article, Constants.KeyExtra.READ_LATER_MAP);
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_mark_read_later), Toast.LENGTH_SHORT).show();
            return;
        }

        ArticleDetailModel.getInstance().markReadLate(articleID, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                ArticleDetailModel.getInstance().addArticleWithKey(context, article, Constants.KeyExtra.READ_LATER_MAP);
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_mark_read_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelRead() {
        if (!GlobalData.getInstance().isLogin()) {
            ArticleDetailModel.getInstance().removeArticleByKey(context, articleID, Constants.KeyExtra.READ_LATER_MAP);
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_cancel_read_later), Toast.LENGTH_SHORT).show();
            return;
        }

        ArticleDetailModel.getInstance().cancelReadLate(articleID, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                ArticleDetailModel.getInstance().removeArticleByKey(context, articleID, Constants.KeyExtra.READ_LATER_MAP);
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_cancel_read_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 取消收藏
     */
    private void cancelFavorite() {
        CollectionModel.getInstance().cancelCollections(articleID, new NetworkObserver<UnFavBean>() {
            @Override
            public void onSuccess(UnFavBean baseBean) {
                setFavorite(baseBean.getLike());
                EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.CANCEL_FAVORITE_ARTICLE));
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_cancel_collection_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 添加收藏
     *
     * @param cat 类别
     */
    private void addFavorite(String cat) {
        CollectionModel.getInstance().addCollections(articleID, cat, new NetworkObserver<UnFavBean>() {
            @Override
            public void onSuccess(UnFavBean baseBean) {
                setFavorite(baseBean.getLike());
                if (journalListDialog != null) {
                    journalListDialog.dismiss();
                }
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_mark_collection_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtils.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 关闭分享面板
     */
    void closeShareAction() {
        if (mShareAction != null) {
            mShareAction.close();
        }
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(context, ((BaseActivity) context).getWindow().getDecorView().getRootView().findViewById(R.id.title_bar));
        popupMenu.getMenuInflater().inflate(R.menu.article_detail_popup_menu, popupMenu.getMenu());
        popupMenu.getMenu().findItem(R.id.menu_add_read).setTitle(mMarkReadOrNotText);
        popupMenu.getMenu().findItem(R.id.menu_add_collection).setTitle(mCollectionOrNotText);
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    private void showAddJournalDialog() {
        if (journalListDialog == null) {
            journalListDialog = new Dialog(context, R.style.SlideBottomDialogStyle);
        }

        AddToJournalViewModel viewModel = new AddToJournalViewModel(context);
        viewModel.setCanEdit(true);
        viewModel.setListener(this);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_add_to_journal_layout, null, false);
        binding.setVariable(BR.addToJournalVM, viewModel);
        binding.executePendingBindings();
        journalListDialog.setContentView(binding.getRoot());
        journalListDialog.show();
    }

    @Override
    public void onAddClick(String cat) {
        addFavorite(cat);
    }

    @Override
    public void onBackClick() {
        if (journalListDialog != null) {
            journalListDialog.dismiss();
        }
    }

    @Override
    public void onSmallClick() {
        if (fontPopup != null) {
            fontPopup.dismiss();
        }
        smallSelected = true;
        middleSelected = false;
        bigSelected = false;
        titleTextSize.set(16);
        dateTextSize.set(10);
        contentTextSize.set(14);
    }

    @Override
    public void onMiddleClick() {
        if (fontPopup != null) {
            fontPopup.dismiss();
        }
        smallSelected = false;
        middleSelected = true;
        bigSelected = false;
        titleTextSize.set(20);
        dateTextSize.set(14);
        contentTextSize.set(18);
    }

    @Override
    public void onBigClick() {
        if (fontPopup != null) {
            fontPopup.dismiss();
        }
        smallSelected = false;
        middleSelected = false;
        bigSelected = true;
        titleTextSize.set(26);
        dateTextSize.set(20);
        contentTextSize.set(24);
    }
}