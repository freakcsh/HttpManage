package com.freak.httpmanage.property;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.freak.httpmanage.R;

public class BatteryActivity extends AppCompatActivity implements View.OnClickListener {
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BatteryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        findViewById(R.id.show_battery).setOnClickListener(this);
        findViewById(R.id.show_battery_1).setOnClickListener(this);
        findViewById(R.id.show_battery_2).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_battery:
                BatteryWhiteList.ignoreBatteryOptimization(this);
                break;
            case R.id.show_battery_1:
                BatteryWhiteList.showBatteryOptimization(this);
                break;
          case R.id.show_battery_2:
                SettingUtils.enterWhiteListSetting(this);
                break;
            default:
                break;
        }
    }
}
