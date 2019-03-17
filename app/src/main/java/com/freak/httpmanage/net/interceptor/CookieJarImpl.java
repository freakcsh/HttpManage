package com.freak.httpmanage.net.interceptor;

import com.freak.httphelper.PersistentCookieStore;
import com.freak.httpmanage.app.App;
import com.freak.httpmanage.net.log.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieJarImpl implements CookieJar {
    private ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
    private PersistentCookieStore mPersistentCookieStore = new PersistentCookieStore(App.getInstance().getApplicationContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        //本地可校验cookie，并根据需要存储
        LogUtil.e("cookie--》\n" + cookies.toString());
        for (Cookie cookie : cookies) {
            mPersistentCookieStore.add(url, cookie);
        }
//        cookieStore.put(url.host(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        ////加载新的cookies
//        List<Cookie> cookies = cookieStore.get(url.host());
        List<Cookie> cookies = mPersistentCookieStore.get(url);
        LogUtil.d("cookie--》\n" + cookies);
        return cookies == null ? new ArrayList<Cookie>() : cookies;
    }
}
