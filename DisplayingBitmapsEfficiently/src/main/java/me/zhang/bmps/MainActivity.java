package me.zhang.bmps;

import android.os.Bundle;


public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void prepareMenu() {
        addMenuItem("1. Loading Large Bitmaps Efficiently", BitmapActivity.class);
        addMenuItem("2. Processing Bitmaps Off the UI Thread", AsyncActivity.class);
        addMenuItem("3. Caching Bitmaps", CacheActivity.class);
    }

}
