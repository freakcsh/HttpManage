package com.freak.httpmanage.rxbus;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.freak.httphelper.RxBus;
import com.freak.httpmanage.R;
import com.freak.httpmanage.app.BaseActivity;
import com.freak.httpmanage.event.RxEvent;
import com.freak.httpmanage.util.ToastUtil;


public class RxBusActivity extends BaseActivity<RxBusPresenter> implements RxBusContract.View{
    public static void startAction(Context context) {
        Intent intent = new Intent(context, RxBusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_rx_bus;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected RxBusPresenter createPresenter() {
        return new RxBusPresenter();
    }

    public void rxBus(View view) {
        RxBus.getDefault().post(new RxEvent("RxFreak", "Rx123456", 1000));
        finish();
    }

    @Override
    public void showResult(String result) {
        ToastUtil.shortShow(result);
    }

    public void test(View view) {
        mPresenter.doTest();
    }
}
