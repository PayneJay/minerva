package com.minerva.business.article.detail;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
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
import com.minerva.utils.CommonUtil;
import com.minerva.utils.DisplayUtil;
import com.minerva.utils.HtmlUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.widget.Loading;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Random;

public class ArticleDetailViewModel extends BaseViewModel implements UMShareListener, PopupMenu.OnMenuItemClickListener, ISelectJournalListener, IFontSelectedListener {
    public ObservableField<String> articleContent = new ObservableField<>("");
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public ObservableInt titleTextSize = new ObservableInt(20);
    public ObservableInt dateTextSize = new ObservableInt(14);
    public ObservableInt contentTextSize = new ObservableInt(18);
    public ObservableInt backgroundColor = new ObservableInt(R.color.colorPrimary);
    public ObservableField<String> description = new ObservableField<>(ResourceUtil.getString(R.string.no_data));
    public ObservableInt drawableRes = new ObservableInt(R.drawable.icon_no_data);
    public ObservableBoolean isException = new ObservableBoolean(true);
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
    private SpeechSynthesizer mTts;

    ArticleDetailViewModel(Context context) {
        super(context);
        articleID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.ARTICLE_ID);
        setBackgroundColor();
        getArticleDetailById(articleID);
    }

    public boolean isNetworkAvailable() {
        return CommonUtil.isNetworkAvailable(context);
    }

    /**
     * 分享
     */
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

    /**
     * 评论
     */
    public void comment() {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(Constants.KeyExtra.ARTICLE_ID, articleID);
        context.startActivity(intent);
    }

    /**
     * 更多菜单
     */
    public void more() {
        final Map<String, Object> readLater = ArticleDetailModel.getInstance().getArticlesByKey(context, Constants.KeyExtra.READ_LATER_MAP);
        if (readLater.keySet().contains(articleID)) {
            //已添加待读，该操作为取消待读
            mMarkReadOrNotText = ResourceUtil.getString(R.string.toolbar_menu_cancel_read);
        } else {
            //未添加待读，执行添加操作
            mMarkReadOrNotText = ResourceUtil.getString(R.string.toolbar_menu_add_read);
        }

        //判断是否收藏
        if (isFav) {
            mCollectionOrNotText = ResourceUtil.getString(R.string.toolbar_menu_cancel_collection);
        } else {
            mCollectionOrNotText = ResourceUtil.getString(R.string.toolbar_menu_add_collection);
        }
        showPopupMenu();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.i("share", throwable.getMessage(), throwable);
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
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
            case R.id.menu_open_voice_broadcast:
                speakText();
                break;
        }
        return true;
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

    @Override
    public void onDetach() {
        super.onDetach();
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

    /**
     * 设置背景色
     */
    private void setBackgroundColor() {
        Random random = new Random();
        int[] colors = new int[]{R.color.colorPrimary, R.color.color_6F00D2,
                R.color.color_1E90FF, R.color.color_7A8B8B, R.color.color_9AC0CD,
                R.color.color_8B658B, R.color.colorAccent};
        int i = random.nextInt(colors.length);
        backgroundColor.set(colors[i]);

        //设置状态栏颜色
        Window window = ((BaseActivity) context).getWindow();
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourceUtil.getColor(colors[i]));
        }
    }

    /**
     * 获取文章详情内容
     *
     * @param articleID 文章id
     */
    private void getArticleDetailById(String articleID) {
        if (!isNetworkAvailable()) {
            isException.set(true);
            description.set(ResourceUtil.getString(R.string.network_error));
            drawableRes.set(R.drawable.icon_network_exception);
            return;
        }
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }
        ArticleDetailModel.getInstance().getArticleDetail(articleID, new NetworkObserver<ArticleDetailBean>() {
            @Override
            public void onSuccess(ArticleDetailBean articleDetailBean) {
                loading.dismiss();
                isException.set(false);
                article = articleDetailBean.getArticle();
                setFavorite(articleDetailBean.getLike());
                if (article != null) {
                    articleContent.set(article.getContent());
                    title.set(article.getTitle());
                    date.set(article.getFeed_title() + "   " + article.getTime());
                    mArticleLink = article.getUrl();
                    //添加到阅读历史
                    ArticleDetailModel.getInstance().addArticleWithKey(context, article, Constants.KeyExtra.READ_HISTORY_MAP);
                }
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
                isException.set(true);
                description.set(ResourceUtil.getString(R.string.no_data));
                drawableRes.set(R.drawable.icon_no_data);
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

    /**
     * 语音播报
     */
    private void speakText() {
        final String html = HtmlUtil.getTextFromHtml(articleContent.get());
        if (TextUtils.isEmpty(html)) {
            return;
        }
        //1. 创建 SpeechSynthesizer 对象 , 第二个参数： 本地合成时传 InitListener
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        Toast.makeText(context, "初始化失败,错误码：" + code, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //3.开始合成
                    mTts.startSpeaking(html, new MySynthesizerListener(context));
                }
            });
        }
        //2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录 13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, getSpeaker()); // 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在 “./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
    }

    @NonNull
    private String getSpeaker() {
        if (article != null){
            if (article.getLang() == 1){
                return "xiaoyan";
            }

            if (article.getLang() == 2){
                return "catherine";
            }
        }
        return "xiaoyan";
    }

    /**
     * 改变字体大小
     */
    private void changeFontSize() {
        if (fontPopup == null) {
            fontPopup = new PopupWindow(((BaseActivity) context).getWindow().getDecorView(), DisplayUtil.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
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

    /**
     * 点击收藏
     */
    private void goMyCollection() {
        if (!GlobalData.getInstance().isLogin()) {
            Toast.makeText(context, ResourceUtil.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
            return;
        }

        if (isFav) {
            cancelFavorite();
        } else {
            showAddFavKanDialog();
        }
    }

    /**
     * 查看原文
     */
    private void viewOriginal() {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.KeyExtra.WEB_URL_LINK, mArticleLink);
        intent.putExtra(Constants.KeyExtra.WEB_VIEW_TITLE, ResourceUtil.getString(R.string.toolbar_menu_view_original));
        context.startActivity(intent);
    }

    /**
     * 复制链接地址
     */
    private void copyLink() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mArticleLink);
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }

        Toast.makeText(context, ResourceUtil.getString(R.string.toast_copy_clipboard), Toast.LENGTH_SHORT).show();
    }

    /**
     * 标记未读或者待读
     */
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

    /**
     * 标记已读
     */
    private void markRead() {
        if (!GlobalData.getInstance().isLogin()) {
            ArticleDetailModel.getInstance().addArticleWithKey(context, article, Constants.KeyExtra.READ_LATER_MAP);
            Toast.makeText(context, ResourceUtil.getString(R.string.toast_mark_read_later), Toast.LENGTH_SHORT).show();
            return;
        }

        ArticleDetailModel.getInstance().markReadLate(articleID, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                ArticleDetailModel.getInstance().addArticleWithKey(context, article, Constants.KeyExtra.READ_LATER_MAP);
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_mark_read_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 取消待读
     */
    private void cancelRead() {
        if (!GlobalData.getInstance().isLogin()) {
            ArticleDetailModel.getInstance().removeArticleByKey(context, articleID, Constants.KeyExtra.READ_LATER_MAP);
            Toast.makeText(context, ResourceUtil.getString(R.string.toast_cancel_read_later), Toast.LENGTH_SHORT).show();
            return;
        }

        ArticleDetailModel.getInstance().cancelReadLate(articleID, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                ArticleDetailModel.getInstance().removeArticleByKey(context, articleID, Constants.KeyExtra.READ_LATER_MAP);
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_cancel_read_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_cancel_collection_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_mark_collection_later), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(context, ResourceUtil.getString(R.string.toast_operate_fail), Toast.LENGTH_SHORT).show();
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

    /**
     * 显示详情页菜单
     */
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(context, ((BaseActivity) context).getWindow().getDecorView().getRootView().findViewById(R.id.title_bar));
        popupMenu.getMenuInflater().inflate(R.menu.article_detail_popup_menu, popupMenu.getMenu());
        popupMenu.getMenu().findItem(R.id.menu_add_read).setTitle(mMarkReadOrNotText);
        popupMenu.getMenu().findItem(R.id.menu_add_collection).setTitle(mCollectionOrNotText);
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    /**
     * 添加收藏时的推刊列表
     */
    private void showAddFavKanDialog() {
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
}
