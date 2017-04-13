package me.zhang.workbench.robolectric;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import me.zhang.workbench.R;

public class WelcomeActivity extends AppCompatActivity {

    public static final String NOPE_YOU_RE_WRONG = "Nope, you're wrong!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final View button = findViewById(R.id.press_me_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.results_text_view)).setText(NOPE_YOU_RE_WRONG);
            }
        });

    }
}