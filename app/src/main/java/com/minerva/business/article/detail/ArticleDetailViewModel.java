package com.minerva.business.article.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.detail.model.ArticleDetailModel;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.collection.MyCollectionActivity;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.common.WebViewActivity;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResouceUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.Map;

public class ArticleDetailViewModel extends BaseViewModel implements UMShareListener, PopupMenu.OnMenuItemClickListener {
    public ObservableField<Spanned> articleContent = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    private ArticleDetailBean.ArticleBean article;
    private ShareAction mShareAction;
    private String articleID;
    private String mArticleLink;

    ArticleDetailViewModel(Context context) {
        super(context);

        articleID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.ARTICLE_ID);
        getArticleDetail(articleID);
    }

    private void getArticleDetail(String articleID) {
        ArticleDetailModel.getInstance().getArticleDetail(articleID, new NetworkObserver<ArticleDetailBean>() {
            @Override
            public void onSuccess(ArticleDetailBean articleDetailBean) {
                article = articleDetailBean.getArticle();
                if (article != null) {
                    articleContent.set(Html.fromHtml(article.getContent()));
                    title.set(article.getTitle());
                    date.set(article.getFeed_title() + "   " + article.getTime());
                    mArticleLink = article.getUrl();
                }
            }

            @Override
            public void onFailure() {
                Log.e(Constants.TAG, "getArticleDetail===>failure");
            }
        });
    }

    public void share() {
        mShareAction = new ShareAction((BaseActivity) context);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER)
                .setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR)
                .setShareboardBackgroundColor(ResouceUtils.getColor(R.color.color_FFFFFF));

        UMWeb web = new UMWeb(Constants.shareBaseUrl + articleID);
        if (article != null) {
            web.setTitle(article.getFeed_title());//标题
            UMImage thumb = new UMImage(context, R.mipmap.icon_launcher);
            web.setThumb(thumb);  //缩略图
            web.setDescription(article.getTitle());//描述
        }

        mShareAction.withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(this).open(config);
    }

    public void comment() {
        Constants.showToast(context);
    }

    public void more() {
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
            case R.id.menu_open_turn_over:
                Constants.showToast(context);
                break;
        }
        return true;
    }

    private void goMyCollection() {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        intent.putExtra(Constants.KeyExtra.ARTICLE_ID, articleID);
        context.startActivity(intent);
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
        cm.setPrimaryClip(mClipData);

        Toast.makeText(context, ResouceUtils.getString(R.string.toast_copy_clipboard), Toast.LENGTH_SHORT).show();
    }

    private void markOrCancelRead() {
        final Map<String, Object> readLater = ArticleDetailModel.getInstance().getReadLater(context);
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
            ArticleDetailModel.getInstance().addReadLater(context, article);
            Toast.makeText(context, ResouceUtils.getString(R.string.toast_mark_read_later), Toast.LENGTH_SHORT).show();
            return;
        }

        ArticleDetailModel.getInstance().markReadLate(articleID, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                ArticleDetailModel.getInstance().addReadLater(context, article);
                Toast.makeText(context, ResouceUtils.getString(R.string.toast_mark_read_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, ResouceUtils.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelRead() {
        if (!GlobalData.getInstance().isLogin()) {
            ArticleDetailModel.getInstance().removeReadLater(context, articleID);
            Toast.makeText(context, ResouceUtils.getString(R.string.toast_cancel_read_later), Toast.LENGTH_SHORT).show();
            return;
        }

        ArticleDetailModel.getInstance().cancelReadLate(articleID, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                ArticleDetailModel.getInstance().removeReadLater(context, articleID);
                Toast.makeText(context, ResouceUtils.getString(R.string.toast_cancel_read_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, ResouceUtils.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
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
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }
}
