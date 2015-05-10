package me.zhang.animation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    FrameLayout mSceneRoot;
    Scene mAScene;
    Scene mAnotherScene;

    boolean isAScene = true;

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
        textView1.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        textView1.setText("Line Text 1");
        textView1.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mViewHierarchy.addView(textView1);


        /**
         * Create a transition instance from a resource file
         */
        final Transition fadeTransitionXml = TransitionInflater.from(this).
                inflateTransition(R.transition.fade_transition);

        /**
         * Create a transition instance in your code
         */
        final Transition fadeTransitionCode = new Fade(Fade.IN|Fade.OUT); // Default Mode
        fadeTransitionCode.setDuration(1000);

        final Transition changeBoundsTransitionCode = new ChangeBounds();
        changeBoundsTransitionCode.setDuration(1000);

        final Transition seqTransitionsXml = TransitionInflater.from(this).
                inflateTransition(R.transition.seq_transitions);

        mSceneRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fadeTransitionXml.addTarget(R.id.text_view1);
//                fadeTransitionXml.addTarget(R.id.text_view2);
                TransitionManager.go(
                        (isAScene = !isAScene) ? mAScene : mAnotherScene,
                        seqTransitionsXml
                );
            }
        });
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
