package com.freak.httphelper.uploading;

import com.freak.httphelper.download.ProgressListener;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;


/**
 * @author Administrator
 */
public class HttpUploadProgressResponseBody extends RequestBody {

    private final RequestBody mRequestBody;
    private BufferedSink mBufferedSink;
    private ProgressListener mListener;

    public HttpUploadProgressResponseBody(RequestBody mRequestBody) {
        this.mRequestBody = mRequestBody;
    }

    public HttpUploadProgressResponseBody(RequestBody mRequestBody, ProgressListener listener) {
        this.mRequestBody = mRequestBody;
        this.mListener = listener;
    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(sink(sink));
        }
        mRequestBody.writeTo(mBufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        mBufferedSink.flush();
    }


    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前进度
            long currentByteLength = 0L;
            //总长度
            long allByteLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);

                currentByteLength = +byteCount;
                if (allByteLength == 0) {
                    allByteLength = contentLength();
                }
                Observable.just(currentByteLength).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (mListener != null) {
                            mListener.onProgress(aLong, allByteLength, aLong == allByteLength);
                        }
                    }
                });


            }
        };
    }

}
