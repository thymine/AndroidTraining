package me.zhang.lab.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import me.zhang.lab.R;
import me.zhang.lab.utils.Utils;

/**
 * Created by Zhang on 2016/3/16 下午 11:29 .
 */
public class AvailableIntentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_available_intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Create your own menu
        inflater.inflate(R.menu.menu_available_intent, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final boolean scanAvailable = Utils.isIntentAvailable(this, "com.google.zxing.client.android.SCAN");
        MenuItem scanItem;
        scanItem = menu.findItem(R.id.action_scan);
        scanItem.setEnabled(scanAvailable);

        final boolean sendAvailable = Utils.isIntentAvailable(this, Intent.ACTION_SENDTO);
        MenuItem emailItem;
        emailItem = menu.findItem(R.id.action_send);
        emailItem.setEnabled(sendAvailable);

        return super.onPrepareOptionsMenu(menu);
    }

}
