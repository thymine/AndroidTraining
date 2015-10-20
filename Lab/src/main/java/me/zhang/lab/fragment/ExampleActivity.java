package me.zhang.lab.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/10/20 下午 2:29 .
 */
public class ExampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_example);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ExampleTopFragment exampleTopFragment = new ExampleTopFragment();
        fragmentTransaction.add(R.id.container_top, exampleTopFragment);
        ExampleBottomFragment exampleBottomFragment = new ExampleBottomFragment();
        fragmentTransaction.add(R.id.container_bottom, exampleBottomFragment);

        fragmentTransaction.commit();
    }
}
