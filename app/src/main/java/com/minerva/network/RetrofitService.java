package com.minerva.network;

import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.site.model.SitesBean;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.SpecialBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nayibo on 2018/3/26.
 */
public interface RetrofitService {
    @GET("/api/articles/hot.json")
    Observable<ArticleBean> getHotArticles(@Query("size") int size, @Query("lang") int lang, @Query("cid") String cid, @Query("is_pad") int isPad);

    @GET("/api/articles/{aid}.json")
    Observable<ArticleDetailBean> getArticleDetail(@Path("aid") String aid, @Query("need_image_meta") int need_image_meta, @Query("type") int type);

    @GET("/api/sites/my_with_dirs.json")
    Observable<SitesBean> getSiteList();

    @GET("/api/mag/home.json")
    Observable<SpecialBean> getSpecialList();

    @GET("/api/books.json")
    Observable<BookBean> getBookList();
}
