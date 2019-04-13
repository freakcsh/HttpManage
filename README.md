# HttpManage
基于Okhttp3+RxJava2+Retrofit2的网络请求框架  
##添加依赖：  
 * 第一步：在project中的build.gradle中添加以下代码
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```  
* 第二步：在app中的build.gradle中添加以下依赖,版本可根据选择区添加不同的版本  
```
	dependencies {
	        implementation 'com.github.freakcsh:HttpManage:V1.3'
	}
```  
##使用方法
###一、初始化  
```
        //设置基地址
        HttpMethods.setBaseUrl(Constants.BASE_URL);
        //设置拦截器，可设置多个
        HttpMethods.setInterceptors(new CommonParametersInterceptor(),new CommonParametersInterceptorHead());
        //设置日志打印级别
        HttpMethods.setLevel(HttpMethods.BODY);
        //设置日志打印
        HttpMethods.setLogger(new HttpLogger());
        //添加自定义的cookieJar保存cookie
        HttpMethods.setCookieJar(new CookieJarImpl());
```  
###二、接口请求
* 1、不抽取结果封装方式  
* 2、抽取结果封装

   




