package me.zhang.androidtraining;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import me.zhang.property_animation.PropertyAnimatedActivity;

public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void prepareMenu() {
        addMenuItem("Property Animation", PropertyAnimatedActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
