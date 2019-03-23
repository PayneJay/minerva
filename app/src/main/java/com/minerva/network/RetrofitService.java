package com.minerva.network;

import com.minerva.base.BaseBean;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.detail.model.ArticleDetailModel;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.category.book.AllBook;
import com.minerva.business.category.mag.model.MagDetailBean;
import com.minerva.business.category.mag.model.MagPeriod;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.business.mine.collection.model.UnFavBean;
import com.minerva.business.mine.login.model.LoginParams;
import com.minerva.business.mine.login.model.UserInfo;
import com.minerva.business.search.model.ArticleResult;
import com.minerva.business.search.model.SiteResult;
import com.minerva.business.site.model.PolymerRead;
import com.minerva.business.site.model.SitesBean;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.MagBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/api/articles/hot.json")
    Observable<ArticleBean> getHotArticles(@Query("size") int size, @Query("lang") int lang, @Query("cid") String cid, @Query("is_pad") int isPad, @Query("last_id") String lastId, @Query("pn") int pn);

    @GET("/api/articles/rec.json")
    Observable<ArticleBean> getRecArticles(@Query("size") int size, @Query("lang") int lang, @Query("cid") String cid, @Query("is_pad") int isPad, @Query("last_id") String lastId, @Query("pn") int pn);

    @GET("/api/articles/{aid}.json")
    Observable<ArticleDetailBean> getArticleDetail(@Path("aid") String aid, @Query("need_image_meta") int need_image_meta, @Query("type") int type);

    @GET("/api/sites/my_with_dirs.json")
    Observable<SitesBean> getSiteList();

    @GET("/api/mag/home.json")
    Observable<MagBean> getSpecialList();

    @GET("/api/books.json")
    Observable<BookBean> getBookList();

    @GET("/api/sites/{aid}.json")
    Observable<ArticleBean> getPeriodicalDetail(@Path("aid") String aid, @Query("pn") int pn, @Query("last_id") String lastId, @Query("size") int size, @Query("is_pad") int isPad);

    @GET("/api/mag/detail.json")
    Observable<MagDetailBean> getMagDetail(@Query("id") String pn, @Query("type") int type);

    @POST("/api/login.json")
    Observable<UserInfo> login(@Body LoginParams params);

    @GET("/api/mag/period_list.json")
    Observable<MagPeriod> getMagPeriodList(@Query("type") int type);

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
    Observable<ArticleBean> getMyCollections(@Query("page") int page, @Query("size") int size, @Query("is_pad") int isPad);

    @FormUrlEncoded
    @POST("/api/articles/do_fav.json")
    Observable<UnFavBean> addFavorite(@Field("id") String id, @Field("cat") String cat);

    @FormUrlEncoded
    @POST("/api/articles/undo_fav.json")
    Observable<UnFavBean> removeFavorite(@Field("id") String id);

    @GET("/api/kans/my.json")
    Observable<KanBean> getKanList();
}
