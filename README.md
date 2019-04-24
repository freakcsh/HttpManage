# HttpManage
基于Okhttp3+RxJava2+Retrofit2的网络请求框架  
## 添加依赖：  
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
## 使用方法  
### 一、初始化  
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
#### 1.3版本以前使用上面的初始化方式，1.3版本以后使用以下初始化方式  
```
  HttpMethods
                .getInstanceBuilder()
                .setBaseUrl(Constants.BASE_URL)//设置域名
                .setLogLevel(LogLevel.ERROR)//设置日志打印级别，使用默认的日志打印才需要设置这个
                .setLogName("123456")//设置默认日志打印名字
                .setIsOpenLog(true)//设置是否开启框架默认的日志打印
                .setCookieJar(new CookieJarImpl())//设置自定义的cookiejar
//                .setLogger(new HttpLogger())//设置自定义logger，此设置是打印网络请求的数据（如果设置了自定义的，则框架默认的则不需要设置）
//                .setLevel(LoggerLevel.BODY)//设置日志打印级别（自定义logger可设置，框架默认的是BODY级别，如果上架需要关闭日志打印，则设置setIsOpenLog(false)即可）
                .setReadTimeOut(60)
                .setConnectTimeOut(60)
                .setWriteTimeOut(60)
//                .setInterceptor(new CommonParametersInterceptor())//设置拦截器
//                .setNetworkInterceptor(new CommonParametersInterceptor())//设置拦截器
//                .setFactory(CustomConverterFactory.create())//设置自定义解析器
                .setInterceptors(new CommonParametersInterceptor(), new CommonParametersInterceptorHead());//设置多个拦截器
```  

### 二、MVP模式构建
* 1、baseView：新建baseView接口，继承RxBaseView，可自定义方法，也可不自定义，示例如下：
```
public interface BaseView extends RxBaseView {
    void showResult(String result);
}
```
* 2、contract：此类起着一个桥梁的作用，把view和presenter使用接口的方式进行通信
```
public interface MainContract {
    interface View extends BaseView {
        void onSuccess(LoginBean loginBean);

        void onSuccess(HttpResult loginBean);

        void onError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void doLogin1(String userName, String pwd);

        void doLogin2(String userName, String pwd);

        void loadLoginStatusEntity();

        void doLogin(String phone, String password);
    }
}
```
* 3、presenter：进行网络请求等操作，示例如下：
```
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    ApiServer apiServer = HttpMethods.getInstance().create(ApiServer.class);

    @Override
    public void doLogin1(String userName, String pwd) {
        Observable observable = apiServer.login1(userName, pwd);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                mView.onSuccess(model);
                Logger.d(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                Logger.d(msg);
            }
        }));
    }

    @Override
    public void doLogin2(String userName, String pwd) {
        Observable<LoginBean> observable = apiServer.login2(userName, pwd).map(new HttpResultFunc<LoginBean>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                Logger.d(model);
                mView.onSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                Logger.d(msg);
            }
        }));
    }




    @Override
    public void loadLoginStatusEntity() {
        Observable<LoginStatusEntity> observable = apiServer.loadLoginStatus();
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginStatusEntity>() {
            @Override
            public void onSuccess(LoginStatusEntity model) {
                LogUtil.e(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                LogUtil.e(msg);
            }
        }));
    }

    @Override
    public void doLogin(String phone, String password) {
        Observable<LoginEntity> observable = apiServer.login(phone, password);
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity model) {
                LogUtil.e(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
                LogUtil.e(msg);
            }
        }));
    }
}
```
注意：presenter类是contract类中Presenter接口的实现类，view层不直接与presenter层交互，
而是通过contract层作为桥梁与presenter层交互，此presenter实现类是要进行网络请求，所有
还需要继承RxPresenter，RxPresenter使用泛型，传入了view对象。  
* 4、view：activity或者fragment。activity和fragment是必须集成activity和fragment的，
但是我们还需要使用到presenter，于是，我们使用了泛型类，所有的activity（或者fragment）
都继承此泛型activity（fragment）基类。  
基类示例：  
```
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements RxBaseView {
    protected T mPresenter;
    protected Activity mActivity;
    private View netErrorView;

    protected abstract int getLayout();

    protected abstract void initEventAndData();

    protected abstract T createPresenter();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /**
         * 创建presenter对象
         */
        mPresenter = createPresenter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        mActivity = this;
        //活动控制器
        App.getInstance().addActivity(this);


        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        initEventAndData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * presenter 解除view订阅
         */
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        App.getInstance().removeActivity(this);

    }

    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;否则返回true
     */
    protected boolean needRegisterNetworkChangeObserver() {
        return true;
    }



    //设置title
    public void setTitleTx(String title_tx) {
        try {
            TextView title = findViewById(R.id.title);
            title.setText(title_tx);
        } catch (Exception e) {

        }
    }

    /**
     * 打开一个Activity 默认 不关闭当前activity
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

```  
activity示例：
```
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, RxView.OnRxViewClickListener {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;
    private Disposable mSubscribe;
    private Button rx_view;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        username = findViewById(R.id.username);
        rx_view = findViewById(R.id.rx_view);
        pwd = findViewById(R.id.pwd);
        tvResult = findViewById(R.id.result);
        mSubscribe = RxBus.getDefault().tObservable(RxEvent.class).subscribe(new Consumer<RxEvent>() {
            @Override
            public void accept(RxEvent rxEvent) throws Exception {
                if (rxEvent.getCode() == 1000) {
                    username.setText(rxEvent.getUserName());
                    pwd.setText(rxEvent.getPassWord());
                }
            }
        });
        RxView.setIntervalTime(2000);
        RxView.setOnClickListeners(this, rx_view);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void onSuccess(LoginBean loginBean) {
        Logger.e("onSuccess");
        Logger.d(loginBean);
        tvResult.setText(loginBean.toString());
    }

    @Override
    public void onSuccess(HttpResult loginBean) {
        tvResult.setText(loginBean.toString());
    }


    @Override
    public void onError(String msg) {
        Log.e(TAG, msg);
        tvResult.setText(msg);
    }


    public void update(View view) {

    }


    public void login(View view) {
        mPresenter.doLogin1(username.getText().toString().trim(), pwd.getText().toString().trim());
    }

    @Override
    public void showResult(String result) {
        tvResult.setText(result);
    }

    public void rxBusOnclick(View view) {
        RxBusActivity.startAction(this);
//        jsonTest();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null) {
            if (mSubscribe.isDisposed()) {
                mSubscribe.dispose();
            }

        }
    }

    public void login2(View view) {
        mPresenter.doLogin2(username.getText().toString().trim(), pwd.getText().toString().trim());
    }

    public void cookieLogin(View view) {
        mPresenter.doLogin("13790994100", "caishouhui0524");
    }

    public void cookieLoginStatus(View view) {
        mPresenter.loadLoginStatusEntity();
    }

    public void downOnclick(View view) {
        DownActivity.startAction(this);
    }

    public void systemDownOnclick(View view) {
        SystemDownloadActivity.startAction(this);
    }

    @Override
    public void onRxViewClick(View view) {
        switch (view.getId()) {
            case R.id.rx_view:
                LogUtil.e("点击了");
                break;
            default:
                break;
        }
    }

    public void downTask(View view) {
        DownTaskListActivity.startAction(this);
    }
}
```




### 三、接口请求  
在activity中进行接口的请求，一句代码：  
mPresenter.doLogin1(username.getText().toString().trim(), pwd.getText().toString().trim());

* 1、不抽取结果封装方式请求  
```
   @Override
      public void doLogin1(String userName, String pwd) {
          Observable observable = apiServer.login1(userName, pwd);
          addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<HttpResult>() {
              @Override
              public void onSuccess(HttpResult model) {
                  mView.onSuccess(model);
                  Logger.d(model);
              }
  
              @Override
              public void onFailure(String msg) {
                  mView.onError(msg);
                  Logger.d(msg);
              }
          }));
      }
```
* 2、抽取结果封装  
```
  @Override
    public void doLogin2(String userName, String pwd) {
        Observable<LoginBean> observable = apiServer.login2(userName, pwd).map(new HttpResultFunc<LoginBean>());
        addSubscription(observable, new SubscriberCallBack<>(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                Logger.d(model);
                mView.onSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                Logger.d(msg);
            }
        }));
    }
```  
## 文件下载  
### 因为每一个人使用的数据库都不一样，下载就不做保存，下载信息类为HttpDownInfo，如需保存，则自己处理即可  
```
    HttpDownMethods mHttpDownMethods;
    String wechatUrl = "http://dldir1.qq.com/weixin/android/weixin703android1400.apk";
    String qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    private HttpDownInfo mDownInfo;
    private HttpDownInfo mDownInfo2;
    private void initDownloads() {
        mHttpDownMethods = HttpDownMethods.getInstance();
        mHttpDownMethods.setDeleteFile(true);
        mDownInfo = new HttpDownInfo();
        mDownInfo.setUrl(qqUrl);
        mDownInfo.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/qq.apk");
        mDownInfo2 = new HttpDownInfo();
        mDownInfo2.setUrl(wechatUrl);
    }
    
     mHttpDownMethods.downStart(mDownInfo, new HttpDownListener() {
                        @Override
                        public void downStart() {
                            btn_download1.setText("暂停");
                        }
    
                        @Override
                        public void downPause(HttpDownInfo httpDownInfo,long progress) {
                            LogUtil.e("暂停了");
                            mDownInfo.setReadLength(progress);
                            btn_download1.setText("下载");
                        }
    
                        @Override
                        public void downStop(HttpDownInfo httpDownInfo) {
                            LogUtil.e("停止了");
                            pb_progress1.setProgress(0);
                            tv_progress1.setText(0 + "%");
                            mDownInfo.setReadLength(0);
                            mDownInfo.setCountLength(0);
                            btn_download1.setText("下载");
                        }
    
                        @Override
                        public void downFinish(HttpDownInfo httpDownInfo) {
                            LogUtil.e("下载完成");
                            mDownInfo = httpDownInfo;
                            btn_download1.setText("下载完成");
                        }
    
                        @Override
                        public void downError(HttpDownInfo httpDownInfo, String msg) {
                            LogUtil.e("出错了");
                            mDownInfo = httpDownInfo;
                            btn_download1.setText("下载出错了");
                        }
    
                        @Override
                        public void downProgress(long readLength, long countLength) {
                            int pro = 0;
                            try {
                                pro = (int) (readLength * 100 / countLength);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            pb_progress1.setProgress(pro);
                            tv_progress1.setText(pro + "%");
                        }
                    });
```  
### RxView使用  
* 1、在需要使用RxView的activity或者fragment中实现接口：RxView.OnRxViewClickListener  
* 2、添加点击事件  
```
  RxView.setIntervalTime(2000);//设置间隔时间
        RxView.setOnClickListeners(this, rx_view,tvResult);//设置需要控制重复点击的按钮，可设置多个
```  
如果RxView使用觉得不方便，可以查看我的博文：  
[Android处理按钮重复点击事件](https://blog.csdn.net/freak_csh/article/details/89477388)  

[网络请求框架（基于okhttp+rxjava2+retrofit2的mvp模式网络请求框架+RxBus+RxView控制按钮重复点击](https://blog.csdn.net/freak_csh/article/details/86712826)  

如果出现9.0系统无法请求网络可以查看博文：
[Android9.0 http网络请求失败问题分析与解决方案](https://blog.csdn.net/freak_csh/article/details/86100031)


   




