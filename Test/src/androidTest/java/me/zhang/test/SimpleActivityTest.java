package me.zhang.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Zhang on 2016/5/6 下午 2:05 .
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SimpleActivityTest {

    @Rule
    public ActivityTestRule<SimpleActivity> activityTestRule = new ActivityTestRule<>(SimpleActivity.class);

    @Test
    public void simpleButtonClick() {
        onView(withId(R.id.simpleButton)).perform(click());
        onView(withId(R.id.simpleText)).check(matches(withText("Hello Espresso!")));
    }

}
