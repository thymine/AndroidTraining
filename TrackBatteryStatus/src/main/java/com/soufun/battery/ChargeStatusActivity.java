package com.soufun.battery;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ChargeStatusActivity extends AppCompatActivity {

    TextView mLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLog = (TextView) findViewById(R.id.id_log);

        if (checkForPower()) {
            mLog.setText("The phone is charging!");
        }
    }

    /**
     * This method checks for power by comparing the current battery state against all possible
     * plugged in states. In this case, a device may be considered plugged in either by USB, AC, or
     * wireless charge. (Wireless charge was introduced in API Level 17.)
     */
    private boolean checkForPower() {
        // It is very easy to subscribe to changes to the battery state, but you can get the current
        // state by simply passing null in as your receiver.  Nifty, isn't that?
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, filter);

        // There are currently three ways a device can be plugged in. We should check them all.
        int chargePlug = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) : 0;
        boolean usbCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB);
        boolean acCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);
        boolean wirelessCharge = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wirelessCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS);
        }
        return (usbCharge || acCharge || wirelessCharge);
    }

}
