package com.minerva.network;

import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.category.column.model.MagDetailBean;
import com.minerva.business.mine.login.model.LoginParams;
import com.minerva.business.mine.login.model.UserInfo;
import com.minerva.business.site.model.SitesBean;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.SpecialBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<SpecialBean> getSpecialList();

    @GET("/api/books.json")
    Observable<BookBean> getBookList();

    @GET("/api/sites/{aid}.json")
    Observable<ArticleBean> getPeriodicalDetail(@Path("aid") String aid, @Query("pn") int pn, @Query("last_id") String lastId, @Query("size") int size, @Query("is_pad") int isPad);

    @GET("/api/mag/detail.json")
    Observable<MagDetailBean> getMagDetailDetail(@Query("id") String pn, @Query("type") int type);

    @POST("/api/login.json")
    Observable<UserInfo> login(@Body LoginParams params);
}
