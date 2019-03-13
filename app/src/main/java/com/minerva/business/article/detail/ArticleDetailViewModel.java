package com.minerva.business.article.detail;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.detail.model.ArticleDetailModel;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResouceUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

public class ArticleDetailViewModel extends BaseViewModel implements UMShareListener {
    public ObservableField<Spanned> articleContent = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    private ShareAction mShareAction;
    private String articleID;
    private ArticleDetailBean.ArticleBean article;

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

        UMWeb web = new UMWeb("http://www.tuicool.com/articles/" + articleID);
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
        Constants.showToast(context);
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
}
