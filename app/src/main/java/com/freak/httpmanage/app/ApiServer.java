package com.freak.httpmanage.app;


import com.freak.httpmanage.bean.BaseBean;
import com.freak.httpmanage.bean.LoginBean;
import com.freak.httpmanage.bean.LoginEntity;
import com.freak.httpmanage.bean.LoginStatusEntity;
import com.freak.httpmanage.net.response.HttpResult;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Observable<HttpResult> login1(@Query("userName") String userName,
                                  @Query("pwd") String pwd);

    /**
     * 用户登陆
     *
     * @return
     */
    @POST("/login")
    Observable<HttpResult<LoginBean>> login2(@Query("userName") String userName,
                                             @Query("pwd") String pwd);

    @POST("/app/user/login")
    Observable<BaseBean> login3(@Query("user_mobile") String user_mobile,
                                @Query("user_password") String user_password);

    /**
     * apk文件下载
     *
     * @param apkUrl
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String apkUrl);

    /**
     * 用户登陆
     *
     * @return
     */
    @POST("login/cellphone")
    Observable<LoginEntity> login(@Query("phone") String phone,
                                  @Query("password") String password
    );

    @POST("login/status")
    Observable<LoginStatusEntity> loadLoginStatus();


    @Multipart
    @POST("user/uploadIdentyfyImg")
    Observable<JsonObject> uploadFile(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> part);

    /**
     * 上传用户身份证图片
     *
     * @param body
     * @return
     */
    @Multipart
    @POST("uploading")
    Observable<HttpResult> uploading(@Query("tip") String tip, @Query("tip1") String tip1, @Part MultipartBody.Part body);

    @Multipart
    @POST("uploading1")
    Observable<HttpResult> uploadingUserPhoto(@Part MultipartBody.Part body);
}
