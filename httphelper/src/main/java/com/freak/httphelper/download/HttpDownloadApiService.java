package com.freak.httphelper.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HttpDownloadApiService {
    @Streaming
    @GET
    Observable<ResponseBody> downFile(@Header("RANGE") String start, @Url String url);
}
