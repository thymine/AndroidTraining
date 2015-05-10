package me.zhang.animation;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zhang on 15-5-10 下午5:45.
 */
public class NoSceneActivity extends Activity {

    TextView mLabelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noscene);

        final EditText inputText = (EditText) findViewById(R.id.inputText);
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Create a new TextView and set some View properties
                mLabelText = new TextView(NoSceneActivity.this);
                mLabelText.setTextSize(18);

                // Get the root view and create a transition
                ViewGroup mRootView = (ViewGroup) findViewById(R.id.mainLayout);
                Fade mFade = new Fade(Fade.IN);
                mFade.setDuration(1000);

                // Start recording changes to the view hierarchy
                TransitionManager.beginDelayedTransition(mRootView, mFade);

                // Add the new TextView to the view hierarchy
                mRootView.addView(mLabelText);

                // When the system redraws the screen to show this update,
                // the framework will animate the addition as a fade in
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLabelText.setText(s.subSequence(start, start + count));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
