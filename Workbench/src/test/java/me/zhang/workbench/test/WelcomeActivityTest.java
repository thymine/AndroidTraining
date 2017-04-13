package me.zhang.workbench.test;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import me.zhang.workbench.BuildConfig;
import me.zhang.workbench.R;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangxiangdong on 2017/4/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class WelcomeActivityTest {

    @Test
    public void clickingButton_shouldChangeResultsViewText() {
        WelcomeActivity activity = Robolectric.setupActivity(WelcomeActivity.class);
        Button pressMeButton = (Button) activity.findViewById(R.id.press_me_button);
        pressMeButton.performClick();

        TextView resultsTextView = (TextView) activity.findViewById(R.id.results_text_view);

        assertEquals(WelcomeActivity.NOPE_YOU_RE_WRONG, resultsTextView.getText().toString());
    }

}