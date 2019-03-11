package com.freak.httpmanage.app;


import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.net.BaseBean;
import com.freak.httpmanage.net.HttpResult;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * @author Administrator
 */
public interface ApiServer {
    /**
     * 用户登陆
     *
     * @return
     */
    @POST("/login")
    Observable<BaseBean> login(@Query("userName") String userName,
                               @Query("pwd") String pwd);

    /**
     * 用户登陆
     *
     * @return
     */
    @POST("/login")
    Observable<HttpResult<LoginBean>> login2(@Query("userName") String userName,
                                             @Query("pwd") String pwd);

    /**
     * apk文件下载
     *
     * @param apkUrl
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String apkUrl);
}
