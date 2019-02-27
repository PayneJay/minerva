package com.minerva.network;

import com.minerva.article.detail.model.ArticleDetailBean;
import com.minerva.article.list.model.ArticleBean;

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


}
