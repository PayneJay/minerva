package com.minerva.network;

import com.minerva.article.model.HotArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nayibo on 2018/3/26.
 */
public interface RetrofitService {
    @GET("/api/articles/hot.json")
    Observable<HotArticleBean> getHotArticles(@Query("size") int size, @Query("lang") int lang, @Query("cid") int cid, @Query("is_pad") int isPad);


}
