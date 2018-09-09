package me.zhang.workbench.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangxiangdong on 2017/6/14.
 */
public class MobileNetworkListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!isOnline(netInfo)) {
            // Publish an mobile network disconnected Event
            EventBus.getDefault().post(new MobileNetDisconnectedEvent());
        } else {
            // Publish an mobile network connected Event
            EventBus.getDefault().post(new MobileNetConnectedEvent(netInfo.getState().toString()));
        }
    }

    public boolean isOnline(NetworkInfo netInfo) {
        // Should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

}
