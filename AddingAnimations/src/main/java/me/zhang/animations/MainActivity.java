package me.zhang.animations;

/**
 * Created by Zhang on 2015/5/11 下午 2:20 .
 */
public class MainActivity extends MenuActivity {
    @Override
    protected void prepareMenu() {
        addMenuItem("CrossFading Two Views", FadeActivity.class);
        addMenuItem("Using ViewPager for Screen Slides", ScreenSlidePageActivity.class);
    }
}
