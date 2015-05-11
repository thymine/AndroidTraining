package com.soufun.animations;

/**
 * Created by Zhang on 2015/5/11 下午 2:20 .
 */
public class StartActivity extends MenuActivity {
    @Override
    protected void prepareMenu() {
        addMenuItem("CrossFading Two Views", FadeActivity.class);
    }
}
