package me.zhang.workbench.test;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2017/4/13.
 */
public class MyActivityRobotiumTest extends ActivityInstrumentationTestCase2<MyActivity> {

    private Solo solo;

    public MyActivityRobotiumTest() {
        super(MyActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testMyActivity() {
        solo.assertCurrentActivity("MyActivity", MyActivity.class);
    }

    public void testSayHello() {
        solo.enterText(0, "Dolly");
        solo.clickOnButton(getActivity().getString(R.string.button));
        solo.assertCurrentActivity("WelcomeActivity", WelcomeActivity.class);
        solo.searchText("Hello, Dolly!");
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }

}
