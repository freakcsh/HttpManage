package com.freak.httphelper.log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2019/4/19.
 */

public class LoggerLevel {
    /**
     * No logs.
     */
    public final static HttpLoggingInterceptor.Level NONE = HttpLoggingInterceptor.Level.NONE;
    /**
     * Logs request and response lines.
     * <p>
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1 (3-byte body)
     *
     * <-- 200 OK (22ms, 6-byte body)
     * }</pre>
     */
    public final static HttpLoggingInterceptor.Level BASIC = HttpLoggingInterceptor.Level.BASIC;
    /**
     * Logs request and response lines and their respective headers.
     * <p>
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     * <-- END HTTP
     * }</pre>
     */
    public final static HttpLoggingInterceptor.Level HEADERS = HttpLoggingInterceptor.Level.HEADERS;
    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     * <p>
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     *
     * Hi?
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     *
     * Hello!
     * <-- END HTTP
     * }</pre>
     */
    public final static HttpLoggingInterceptor.Level BODY = HttpLoggingInterceptor.Level.BODY;
}
