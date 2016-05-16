package me.zhang.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Created by Zhang on 2016/5/16 下午 4:23 .
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdapterViewActivityTest {

    @Rule
    public ActivityTestRule<AdapterViewActivity> activityTestRule = new ActivityTestRule<>(AdapterViewActivity.class);

    @Test
    public void testSpinner() {
        onView(withId(R.id.spinner)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("International"))).perform(click());

        onView(withId(R.id.text)).check(matches(withText(containsString("International"))));
    }

}
