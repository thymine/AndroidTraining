package me.zhang.bmps;

import android.os.Bundle;


public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void prepareMenu() {
        addMenuItem("Loading Large Bitmaps Efficiently", BitmapActivity.class);
        addMenuItem("Processing Bitmaps Off the UI Thread", AsyncActivity.class);
        addMenuItem("Caching Bitmaps", CacheActivity.class);
    }

}
