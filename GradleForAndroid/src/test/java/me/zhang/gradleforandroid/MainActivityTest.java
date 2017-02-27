package me.zhang.gradleforandroid;

import android.content.Intent;
import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by zhangxiangdong on 2017/2/27.
 */
@RunWith(RobolectricTestRunner.class)
// http://robolectric.org/getting-started/
// https://github.com/robolectric/robolectric/issues/2625
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class MainActivityTest {

    @Test
    public void clickingLogin_shouldStartLoginActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.login).performClick();

        Intent expectedIntent = new Intent(activity, LoginActivity.class);

        // https://github.com/robolectric/robolectric/issues/2521
//        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent)); // Not passed
        assertThat(shadowOf(activity).getNextStartedActivity().toString().trim(),
                equalTo(expectedIntent.toString().trim()));
    }

}