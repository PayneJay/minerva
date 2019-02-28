package com.minerva.article.detail;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.article.detail.model.ArticleDetailBean;
import com.minerva.article.detail.model.ArticleDetailModel;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResouceUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.ShareBoardConfig;

public class ArticleDetailViewModel extends BaseViewModel implements UMShareListener {
    private String content = "<div> \n" +
            " <div> \n" +
            "  <iframe src=\"http://v.qq.com/iframe/player.html?vid=u0842dabbbs&amp;auto=0\" width=\"100%\" height=\"100%\" frameborder=\"0\" allowfullscreen=\"true\"></iframe> \n" +
            " </div> \n" +
            " <h4>苹果联合创始人沃兹尼亚克：我真的很想要可折叠手机</h4> \n" +
            " <p> <img src=\"https://img0.tuicool.com/emQfue3.png!web\" class=\"alignCenter\" /> </p> \n" +
            " <p>腾讯科技讯 据外媒报道，苹果公司联合创始人史蒂夫-沃兹尼亚克(Steve Wozniak)已迫不及待地想要可折叠iPhone。沃兹尼亚克在接受彭博电视台(Bloomberg TV)采访时表示：“苹果在Touch ID指纹识别技术、面部识别技术和手机支付等几个领域中一直处于领先地位。但他们不是可折叠手机领域的领先者，这一点让我很担心，因为我真的很想要一部可折叠手机。”</p> \n" +
            " <p>沃兹尼亚克指的是苹果竞争对手三星电子和华为刚刚推出的那种可折叠手机。它们采取了苹果的高价手机策略，可折叠手机的定价分别为1980美元和2600美元左右。</p> \n" +
            " <h4>不再分享5G芯片成果？英特尔与紫光展锐相关合作伙伴关系终止</h4> \n" +
            " <p> <img src=\"https://img0.tuicool.com/FvIZf2.jpg!web\" class=\"alignCenter\" /> </p> \n" +
            " <p>今日有消息称，由于担心相关技术转让会在华盛顿惹出问题，英特尔终止了其与清华紫光集团子公司紫光展锐在分享最新5G调制解调器芯片成果方面的合作伙伴关系。第一财经就此在MWC期间向两家公司求证。对此，英特尔方面表示，“英特尔和紫光展锐共同决定终止合作。这完全是商业决定。”紫光展锐方面表示不予置评。</p> \n" +
            " <h4>法拉第未来：数百名休假员工下周无法重返工作岗位</h4> \n" +
            " <p> <img src=\"https://img0.tuicool.com/ZR3Irai.jpg!web\" class=\"alignCenter\" /> </p> \n" +
            " <p>腾讯科技讯 在美国资本市场和科技行业，激进股东是不可忽视的角色，他们可以通过代理人大战，获取公司控制权或董事席位，逼迫董事会和管理层对公司业务作出重大调整，尤其是变卖业务。据外媒最新消息，知情人士透露，美国电子商务巨头eBay公司正在与两家激进股东埃利奥特资产管理公司(ElliottManagementCorp.)和右舷价值基金(Starboard Value)进行和解谈判，这可能导致维权投资者避免围绕这个在线市场发生代理人大战。</p> \n" +
            " <h4>传eBay向激进股东妥协 公司或被肢解变卖</h4> \n" +
            " <p> <img src=\"https://img1.tuicool.com/mqEr2uF.jpg!web\" class=\"alignCenter\" /> </p> \n" +
            " <p>腾讯科技讯 2月27日消息，据外媒报道，美国科技媒体The Verge周二获得的一封内部电子邮件现实，电动汽车初创企业法拉第未来(Faraday Future)已经通知正在无薪休假的数百名员工，他们不能按计划于3月1日重返工作岗位。法拉第未来表示，该公司将延长员工的休假时间，但没有具体说明延长日期。自2018年12月份以来，这些员工始终处于休假状态，但可继续享受公司福利待遇。</p> \n" +
            " <h4>小米组织架构调整成立技术委员会 强化技术引领 增强互联网成色</h4> \n" +
            " <p> <img src=\"https://img1.tuicool.com/uUnIriV.jpg!web\" class=\"alignCenter\" /> </p> \n" +
            " <p>【TechWeb】今日，小米集团组织部下发正式文件，宣布了最新一轮组织架构调整，任命了崔宝秋为集团副总裁，集团技术委员会主席，并且在核心管理岗位上共任命了14名总经理、副总经理，这也是继去年9月成立集团组织部、参谋部以来，小米规模最大的一次组织架构调整。雷军在内部会议上指出，要继续强化技术立业，技术事关小米生死存亡，是小米持续发展最重要的动力和引擎。</p> \n" +
            "</div>";
    public ObservableField<Spanned> articleContent = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>("【动点播报】英特尔与紫光展锐终止合作关系，FF 数百名休假员工下周无法返岗");
    public ObservableField<String> date = new ObservableField<>("动点科技 02-27 18:36");
    private ShareAction mShareAction;

    ArticleDetailViewModel(Context context) {
        super(context);

        String articleID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.ARTICLE_ID);
        articleContent.set(Html.fromHtml(content));
        getArticleDetail(articleID);
    }

    private void getArticleDetail(String articleID) {
        ArticleDetailModel.getInstance().getArticleDetail(articleID, new NetworkObserver<ArticleDetailBean>() {
            @Override
            public void onSuccess(ArticleDetailBean articleDetailBean) {
                Log.e(Constants.TAG, "getArticleDetail===>success " + articleDetailBean.isSuccess());
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

        mShareAction.withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(this).open(config);
    }

    public void comment() {
        Toast.makeText(context, "评论，敬请期待……", Toast.LENGTH_SHORT).show();
    }

    public void more() {
        Toast.makeText(context, "更多，敬请期待……", Toast.LENGTH_SHORT).show();
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
