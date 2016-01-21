package me.zhang.lab.memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        if (leaky == null)
            leaky = new Leaky();
    }
}
