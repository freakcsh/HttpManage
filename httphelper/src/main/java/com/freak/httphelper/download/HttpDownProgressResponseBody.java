package com.freak.httphelper.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class HttpDownProgressResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private ProgressListener mProgressListener;
    private BufferedSource mBufferedSource;

    public HttpDownProgressResponseBody(ResponseBody body, ProgressListener progressListener) {
        mResponseBody = body;
        mProgressListener = progressListener;
    }

    /**
     * @return 返回响应内容的类型，比如image/jpeg
     */
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    /**
     * @return 返回响应内容的长度
     */
    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(BufferedSource source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long byteRead = super.read(sink, byteCount);
                //不断统计当前下载好的数据
                totalBytesRead += byteRead != -1 ? byteRead : 0;
                //接口回调
                if (mProgressListener != null) {
                    mProgressListener.onProgress(totalBytesRead, mResponseBody.contentLength(), totalBytesRead == -1);
                }
                return byteRead;
            }
        };
    }
}
