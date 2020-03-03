package com.freak.httphelper.uploading;

import androidx.annotation.NonNull;

import com.freak.httphelper.download.ProgressListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * @author Administrator
 */
public class MultipartUtil {

    private static MultipartUtil sMultipartUtil;

    private Map<String, String> maps = new HashMap<>();

    public MultipartUtil() {

    }

    public static MultipartUtil getInstance() {
        if (sMultipartUtil == null) {
            synchronized (MultipartUtil.class) {
                sMultipartUtil = new MultipartUtil();
            }
        }
        return sMultipartUtil;
    }

    public MultipartUtil addParam(String key, String value) {
        maps.put(key, value);
        return sMultipartUtil;
    }

    public Map<String, RequestBody> Build() {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (String key : maps.keySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), maps.get(key));
            bodyMap.put(key, body);
        }
        return bodyMap;
    }


    public static List<MultipartBody.Part> makeMultipart(String key, @NonNull List<File> files) {
        return makeMultipart(key, files, null);
    }

    public static MultipartBody.Part makeMultipart(String key, @NonNull File file) {
        return makeMultipart(key, file, null);
    }

    public static MultipartBody.Part makeMultipart(String key, @NonNull File file, ProgressListener listener) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (file == null || !file.exists()) {
            try {
                throw new UploadRequestException("文件为null");
            } catch (UploadRequestException e) {
                e.printStackTrace();
            }
            return null;
        }
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        HttpUploadProgressResponseBody upLoadBody = new HttpUploadProgressResponseBody(body, listener);
        builder.addFormDataPart(key, file.getName(), upLoadBody);
        return builder.build().parts().get(0);
    }


    /**
     * @param key      文件key
     * @param files    文件
     * @param listener 文件上传进度监听，只支持一个文件的时候使用，多文件不生效
     * @return List<MultipartBody.Part>
     */
    public static List<MultipartBody.Part> makeMultipart(String key, @NonNull List<File> files, ProgressListener listener) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (files.size() > 1) {
            listener = null;
        }
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i) == null) {
                try {
                    throw new UploadRequestException("文件为null");
                } catch (UploadRequestException e) {
                    e.printStackTrace();
                }
                return null;
            }

            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i));
            HttpUploadProgressResponseBody upLoadBody = new HttpUploadProgressResponseBody(body, listener);
            builder.addFormDataPart(key, files.get(i).getName(), upLoadBody);
        }
        return builder.build().parts();
    }

}
