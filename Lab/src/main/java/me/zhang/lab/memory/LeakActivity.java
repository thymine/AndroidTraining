package me.zhang.lab.memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.zhang.lab.R;

public class LeakActivity extends AppCompatActivity {

    static Leaky leaky = null;

    class Leaky {

        void doSomething() {
            System.out.println("Wheee!!!");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate(...)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        if (leaky == null) {
            System.out.println("leaky == null");
            leaky = new Leaky();
        }

        leaky.doSomething();
    }

    @Override
    protected void onStart() {
        System.out.println("onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        System.out.println("onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop()");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        System.out.println("onRestart()");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy()");
        super.onDestroy();
    }
}
