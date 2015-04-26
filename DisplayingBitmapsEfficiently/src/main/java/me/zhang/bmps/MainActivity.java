package me.zhang.bmps;

import android.os.Bundle;


public class MainActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void prepareMenu() {
        addMenuItem("Loading Large Bitmaps Efficiently", AlphaActivity.class);
        addMenuItem("Processing Bitmaps Off the UI Thread", BetaActivity.class);
    }

}
