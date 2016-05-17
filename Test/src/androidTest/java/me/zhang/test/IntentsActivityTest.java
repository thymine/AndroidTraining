package me.zhang.test;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Zhang on 2016/5/17 上午 10:19 .
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntentsActivityTest {

    @Rule
    public IntentsTestRule<IntentsActivity> intentsTestRule = new IntentsTestRule<>(IntentsActivity.class);

    @Test
    public void validateIntentSentToAdapterViewActivity() {

    }

}
