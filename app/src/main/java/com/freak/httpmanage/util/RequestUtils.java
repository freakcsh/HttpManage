package com.freak.httpmanage.util;

import android.text.TextUtils;

import com.freak.httphelper.log.LogUtil;
import com.freak.httpmanage.app.App;
import com.freak.httpmanage.app.Constants;
import com.freak.httpmanage.upload.FileUploadObserver;
import com.freak.httpmanage.upload.UploadFileRequestBody;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class RequestUtils {

    public static InnerParam newParams() {
        return new InnerParam();
    }

    public static InnerParam newParams(boolean isWithToken) {
        return new InnerParam(isWithToken);
    }

    public static class InnerParam {

        Map<String, Object> mMap;

        public InnerParam() {
            this(true);
        }

        public InnerParam(boolean isWithToken) {
            mMap = new HashMap<>();
//            if (isWithToken) {
//                mMap.put("token", TextUtils.isEmpty((String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, "")) ? "" : (String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, ""));
//            }
        }

        public InnerParam addParams(String key, String value) {
            mMap.put(key, value);
            return this;
        }

        public InnerParam addParams(String key, Object value) {
            mMap.put(key, value);
            return this;
        }

        public InnerParam addParams(String key, int value) {
            mMap.put(key, value + "");
            return this;
        }

        public InnerParam addParams(String key, long value) {
            mMap.put(key, value + "");
            return this;
        }

        public InnerParam addParams(String key, double value) {
            mMap.put(key, value + "");
            return this;
        }

        public Map<String, Object> create() {
            return mMap;
        }

        public RequestBody createRequestBody() {
            return transformToRequestBody(mMap);
        }

        public RequestBody createRequestMultipartBody(File file, String fileKey) {
            return transformToRequestMultipartBody(mMap, file, fileKey);
        }

        public RequestBody createRequestMultipartBodyWithProgress(File file, String fileKey, FileUploadObserver<ResponseBody> fileUploadObserver) {
            return transformToRequestMultipartBodyWithProgress(mMap, file, fileKey, fileUploadObserver);
        }

    }

    public static RequestBody transformToRequestBody(Map<String, Object> requestDataMap) {
        requestDataMap.put("token", TextUtils.isEmpty((String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, "")) ? "" : (String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, ""));
        LogUtil.e("请求参数--》" + requestDataMap.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(requestDataMap));
        return requestBody;
    }

    /**
     * 文件加参数混合上传
     * 例子： @POST("api/staff/v1/staff/update_avatar")
     * Observable<HttpResult<UpLoadEntity>> upLoad(@Body RequestBody body);
     *
     * @param requestDataMap
     * @param file
     * @param fileKey
     * @return
     */
    public static RequestBody transformToRequestMultipartBody(Map<String, Object> requestDataMap, File file, String fileKey) {
        requestDataMap.put("token", TextUtils.isEmpty((String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, "")) ? "" : (String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, ""));
        LogUtil.e("请求参数--》" + requestDataMap.toString());
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(fileKey, file.getName(), body);
        }
        for (Map.Entry<String, Object> entry : requestDataMap.entrySet()) {
            builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
        }
        RequestBody requestBody = builder.build();
        return requestBody;
    }

    /**
     * 带进度文件加参数混合上传
     * 例子： @POST("api/staff/v1/staff/update_avatar")
     * Observable<HttpResult<UpLoadEntity>> upLoad(@Body RequestBody body);
     *
     * @param requestDataMap
     * @param file
     * @param fileKey
     * @return
     */
    public static RequestBody transformToRequestMultipartBodyWithProgress(Map<String, Object> requestDataMap, File file, String fileKey, FileUploadObserver<ResponseBody> fileUploadObserver) {
        requestDataMap.put("token", TextUtils.isEmpty((String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, "")) ? "" : (String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, ""));
        LogUtil.e("请求参数--》" + requestDataMap.toString());
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {

            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, fileUploadObserver);
            builder.addFormDataPart(fileKey, file.getName(), uploadFileRequestBody);
        }
        for (Map.Entry<String, Object> entry : requestDataMap.entrySet()) {
            builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
        }

        RequestBody requestBody = builder.build();
        return requestBody;
    }

    /**
     * 单文件上传
     * 例子： @Multipart
     * #     @POST("api/other/v1/upload") Observable<HttpResult> upLoad(@Part MultipartBody.Part body);
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part transformToRequestMultipartBodyPart(File file) {
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
        return part;
    }

    /**
     * 带进度单文件上传
     * 例子： @Multipart
     * #     @POST("api/other/v1/upload") Observable<HttpResult> upLoad(@Part MultipartBody.Part body);
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part transformToRequestMultipartBodyPartWithProgress(File file, FileUploadObserver<ResponseBody> fileUploadObserver) {
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, fileUploadObserver);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), uploadFileRequestBody);
        return part;
    }
}
