package com.minerva.network;

import com.minerva.base.BaseBean;
import com.minerva.business.article.comment.model.CommentDetail;
import com.minerva.business.article.comment.model.CommentListBean;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.list.model.ArticleListBean;
import com.minerva.business.category.book.model.AllBook;
import com.minerva.business.category.mag.model.MagDetailBean;
import com.minerva.business.category.mag.model.MagPeriodBean;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.MagBean;
import com.minerva.business.home.subscribe.model.SubscribeBean;
import com.minerva.business.home.weekly.model.WeekDetailBean;
import com.minerva.business.home.weekly.model.WeekListBean;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.business.mine.collection.model.UnFavBean;
import com.minerva.business.mine.journal.kan.model.FavKanBean;
import com.minerva.business.mine.message.model.MsgListBean;
import com.minerva.business.mine.signinout.model.LoginParams;
import com.minerva.business.mine.signinout.model.OauthParams;
import com.minerva.business.mine.signinout.model.UserInfo;
import com.minerva.business.search.model.ArticleResult;
import com.minerva.business.search.model.SiteResult;
import com.minerva.business.settings.model.ReadSettingBean;
import com.minerva.business.site.detail.SiteDetailBean;
import com.minerva.business.site.model.PolymerRead;
import com.minerva.business.site.model.SitesBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitService {
    @GET("/api/articles/hot.json")
    Observable<ArticleListBean> getHotArticles(@Query("size") int size, @Query("lang") int lang, @Query("cid") String cid, @Query("is_pad") int isPad, @Query("last_id") String lastId, @Query("pn") int pn);

    @GET("/api/articles/rec.json")
    Observable<ArticleListBean> getRecArticles(@Query("size") int size, @Query("lang") int lang, @Query("cid") String cid, @Query("is_pad") int isPad, @Query("last_id") String lastId, @Query("pn") int pn);

    @GET("/api/articles/{aid}.json")
    Observable<ArticleDetailBean> getArticleDetail(@Path("aid") String aid, @Query("need_image_meta") int need_image_meta, @Query("type") int type);

    @GET("/api/sites/my_with_dirs.json")
    Observable<SitesBean> getSiteList();

    @GET("/api/mag/home.json")
    Observable<MagBean> getSpecialList();

    @GET("/api/books.json")
    Observable<BookBean> getBookList();

    @GET("/api/sites/{aid}.json")
    Observable<SiteDetailBean> getPeriodicalDetail(@Path("aid") String aid, @Query("pn") int pn, @Query("last_id") String lastId, @Query("size") int size, @Query("is_pad") int isPad);

    @GET("/api/mag/detail.json")
    Observable<MagDetailBean> getMagDetail(@Query("id") String pn, @Query("type") int type);

    @POST("/api/login.json")
    Observable<UserInfo> login(@Body LoginParams params);

    @GET("/api/mag/period_list.json")
    Observable<MagPeriodBean> getMagPeriodList(@Query("type") int type);

    @GET("/api/books/tag.json")
    Observable<AllBook> getAllBookList(@Query("tag") int tag, @Query("pn") int pn);

    @GET("/api/sites/juhe_reading.json")
    Observable<PolymerRead> getPolymerReadList(@Query("id") int tag, @Query("pn") int pn, @Query("code") String code, @Query("lang") int lang);

    @GET("/api/articles/search.json")
    Observable<ArticleResult> searchByKeyWord(@Query("pn") int pn, @Query("kw") String kwyWord, @Query("lang") int lang);

    @GET("/api/sites/search.json")
    Observable<SiteResult> searchByKeyWord(@Query("kw") String kwyWord);

    @GET("/api/search/book.json")
    Observable<AllBook> searchByKeyWord(@Query("pn") int pn, @Query("kw") String kwyWord);

    @FormUrlEncoded
    @POST("/api/articles/mark_late.json")
    Observable<BaseBean> markReadLate(@Field("article_id") String id);

    @FormUrlEncoded
    @POST("/api/articles/mark_read.json")
    Observable<BaseBean> cancelReadLate(@Field("article_id") String id);

    @FormUrlEncoded
    @POST("/api/articles/log.json")
    Observable<BaseBean> addCollection(@Field("article_id") String id);

    @GET("/api/articles/my.json")
    Observable<ArticleListBean> getMyCollections(@Query("page") int page, @Query("size") int size, @Query("is_pad") int isPad);

    @FormUrlEncoded
    @POST("/api/articles/do_fav.json")
    Observable<UnFavBean> addFavorite(@Field("id") String id, @Field("cat") String cat);

    @FormUrlEncoded
    @POST("/api/articles/undo_fav.json")
    Observable<UnFavBean> removeFavorite(@Field("id") String id);

    @GET("/api/kans/my.json")
    Observable<KanBean> getKanList();

    @GET("/api/kans/{id}.json")
    Observable<FavKanBean> getFavKansById(@Path("id") String id);

    @FormUrlEncoded
    @POST("/api/kans.json")
    Observable<KanBean> createJournal(@Field("name") String name, @Field("desc") String desc, @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/kans/update_info.json")
    Observable<KanBean> updateKan(@Field("id") String id, @Field("name") String name, @Field("desc") String desc, @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/kans/migrate.json")
    Observable<BaseBean> migrateKan(@Field("id") String id, @Field("target_id") String targetId);

    @FormUrlEncoded
    @POST("/api/kans/delete.json")
    Observable<BaseBean> deleteKanById(@Field("id") String id);

    @GET("/api/articles/late.json")
    Observable<ArticleListBean> getLateList(@Query("size") int size, @Query("is_pad") int isPad);

    @GET("/api/notifications/list.json")
    Observable<MsgListBean> getMessageList();

    @GET("/api/settings/read.json")
    Observable<ReadSettingBean> getReadSetting();

    @FormUrlEncoded
    @POST("/api/settings/update_read.json")
    Observable<BaseBean> updateRead(@Field("tech") String tech, @Field("design") String design, @Field("guru") String guru);

    @GET("/api/weekly/my.json")
    Observable<WeekListBean> getWeeklyList();

    @GET("/api/weekly/{id}.json")
    Observable<WeekDetailBean> getWeeklyDetail(@Path("id") String id);

    @FormUrlEncoded
    @POST("/api/source_groups/add_new.json")
    Observable<SitesBean> createGroup(@Field("name") String name, @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/source_groups/sort_groups.json")
    Observable<BaseBean> sortGroups(@Field("order") String name, @Field("type") int type);

    @POST("/api/sites/mark_all_read.json")
    Observable<BaseBean> markAllRead();

    @FormUrlEncoded
    @POST("/api/sites/mark_juhe_readed.json")
    Observable<BaseBean> markJuheRead(@Field("did") int did);

    @FormUrlEncoded
    @POST("/api/sites/mark_unfollow.json")
    Observable<BaseBean> markUnFollow(@Field("id") String id);

    @FormUrlEncoded
    @POST("/api/source_groups/rename.json")
    Observable<BaseBean> renameGroup(@Field("id") int id, @Field("name") String name, @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/source_groups/transfer_items.json")
    Observable<BaseBean> transferItems(@Field("tid") int tid, @Field("fid") int fid, @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/source_groups/move.json")
    Observable<BaseBean> moveSource(@Field("did") int did, @Field("sid") String sid, @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/source_groups/remove.json")
    Observable<BaseBean> removeGroup(@Field("did") int did, @Field("type") int type);

    @GET("/api/sites/hot.json")
    Observable<SubscribeBean> getSubscribeSite(@Query("cid") String cid);

    @FormUrlEncoded
    @POST("/api/sites/mark_follow.json")
    Observable<BaseBean> markFollow(@Field("id") String id);

    @GET("/api/comments/{aid}.json")
    Observable<CommentListBean> getCommentsByAid(@Path("aid") String aid, @Query("show_link") int showLink, @Query("pn") int pn, @Query("size") int size);

    @FormUrlEncoded
    @POST("/api/comments.json")
    Observable<CommentDetail> submitComment(@Field("aid") String aid, @Field("content") String content, @Field("show_link") int showLink);

    @FormUrlEncoded
    @POST("/api/comments/delete.json")
    Observable<BaseBean> deleteCommentById(@Field("id") String id);

    @FormUrlEncoded
    @POST("/api/signup/register_by_email.json")
    Observable<BaseBean> registerByEmail(@Field("email") String email, @Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/signup/resend_register.json")
    Observable<BaseBean> resendRegister(@Field("email") String email);

    @FormUrlEncoded
    @POST("/api/signup/check_confirm.json")
    Observable<BaseBean> checkConfirm(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/users/update_password.json")
    Observable<BaseBean> updatePassword(@Field("old") String oldPassword, @Field("pwd") String password);

    @FormUrlEncoded
    @POST("/api/users/update_info.json")
    Observable<UserInfo> updateUserInfo(@Field("name") String name);

    @FormUrlEncoded
    @POST("/api/users/update_email.json")
    Observable<UserInfo> updateUserEmail(@Field("email") String email);

    @GET("/api/users/my_info.json")
    Observable<UserInfo> getUserInfo();

    @POST("/api/signup/connect_with_social.json")
    Observable<UserInfo> loginByOauth(@Body OauthParams params);

    @FormUrlEncoded
    @POST("/api/users/cancel_social.json")
    Observable<UserInfo> cancelSocial(@Field("type") int type, @Field("from") int from);

    @FormUrlEncoded
    @POST("/api/signup/follow_sites.json")
    Observable<BaseBean> followSite(@Field("items") String items);

    @GET("/api/signup/cold_sites.json")
    Observable<SitesBean> getColdSiteList();

    //下载文件
    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);
}