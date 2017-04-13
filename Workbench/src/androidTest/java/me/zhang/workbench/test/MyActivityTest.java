package me.zhang.workbench.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static me.zhang.workbench.R.id;
import static me.zhang.workbench.R.string;

/**
 * Created by zhangxiangdong on 2017/4/13.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class MyActivityTest extends ActivityInstrumentationTestCase2<MyActivity> {

    private MyActivity activity;
    private TextView textView;
    private EditText editText;
    private Button helloButton;

    public MyActivityTest() {
        super(MyActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
        textView = (TextView) activity.findViewById(id.text_view);
        editText = (EditText) activity.findViewById(id.edit_text);
        helloButton = (Button) activity.findViewById(id.hello_button);
    }

    @Test
    public void testPreconditions() {
        assertNotNull("Activity is null", activity);
        assertNotNull("TextView is null", textView);
        assertNotNull("EditText is null", editText);
        assertNotNull("HelloButton is null", helloButton);
    }

    @Test
    public void textView_label() {
        final String expected = activity.getString(string.hello_world);
        final String actual = textView.getText().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void editText_hint() {
        final String expected = activity.getString(string.hint_name);
        final String actual = editText.getHint().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void helloButton_label() {
        final String expected = activity.getString(string.button);
        final String actual = helloButton.getText().toString();
        assertEquals(expected, actual);
    }

}