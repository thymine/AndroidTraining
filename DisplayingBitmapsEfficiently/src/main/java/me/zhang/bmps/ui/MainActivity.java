package me.zhang.bmps.ui;

import android.os.Bundle;


public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void prepareMenu() {
        addMenuItem("Loading Large Bitmaps Efficiently", LoadingActivity.class);
        addMenuItem("Processing Bitmaps Off the UI Thread", ProcessingActivity.class);
        addMenuItem("Caching Bitmaps", CachingActivity.class);
    }

}
