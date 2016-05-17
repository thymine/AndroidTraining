package me.zhang.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;

/**
 * Created by Zhang on 2016/5/17 上午 10:15 .
 */
public class IntentsActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdapterViewActivity.class);
                intent.putExtra("name", "Zhang");
                intent.putExtra("age", 23);
                startActivity(intent);
            }
        });

    }
}
