package com.freak.httpmanage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.freak.httphelper.RxBus;
import com.freak.httpmanage.event.RxEvent;


public class RxBusActivity extends Activity {
    public static void startAction(Context context) {
        Intent intent = new Intent(context, RxBusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
    }

    public void rxBus(View view) {
//        RxBus.getDefault().post(new RxEvent("RxFreak", "Rx123456", 1000));
        finish();
    }
}
