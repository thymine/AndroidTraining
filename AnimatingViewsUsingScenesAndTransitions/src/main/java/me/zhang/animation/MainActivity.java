package me.zhang.animation;

/**
 * Created by zhang on 15-5-10 下午5:44.
 */
public class MainActivity extends MenuActivity {
    @Override
    protected void prepareMenu() {
        addMenuItem("Applying a Transition", SceneActivity.class);
        addMenuItem("Apply a Transition Without Scenes", NoSceneActivity.class);
    }
}
