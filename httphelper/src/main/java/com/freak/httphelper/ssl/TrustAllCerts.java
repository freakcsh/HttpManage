package com.freak.httphelper.ssl;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class TrustAllCerts implements X509TrustManager {
    // 检查客户端证书
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
    // 检查服务器端证书
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
    // 返回受信任的X509证书数组
    @Override
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
}
