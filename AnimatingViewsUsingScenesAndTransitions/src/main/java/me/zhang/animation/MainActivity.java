package me.zhang.animation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transition.Scene;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    FrameLayout mSceneRoot;
    FrameLayout mAnotherSceneRoot;
    Scene mAScene;
    Scene mAnotherScene;

    Scene mScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Generate Scenes from Layouts
         */
        // Create the scene root for the scenes in this app
        mSceneRoot = (FrameLayout) findViewById(R.id.scene_root);

        // Create the scenes
        mAScene = Scene.getSceneForLayout(mSceneRoot, R.layout.a_scene, this);
        mAnotherScene =
                Scene.getSceneForLayout(mSceneRoot, R.layout.another_scene, this);

        /**
         * Create a Scene in Your Code
         */
        // Obtain the view hierarchy to add as a child of
        // the scene root when this scene is entered
        LinearLayout mViewHierarchy = new LinearLayout(this);
        mViewHierarchy.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        mViewHierarchy.setOrientation(LinearLayout.VERTICAL);

        TextView textView1 = new TextView(this);
        ViewGroup.LayoutParams params = textView1.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        textView1.setText("Line Text 1");
        mViewHierarchy.addView(textView1);

        mAnotherSceneRoot = (FrameLayout) findViewById(R.id.another_scene_root);
        mScene = new Scene(mAnotherSceneRoot, mViewHierarchy);
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
