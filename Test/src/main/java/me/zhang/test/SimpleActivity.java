package me.zhang.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhang on 2016/5/6 下午 1:52 .
 */
public class SimpleActivity extends AppCompatActivity {

    @BindView(R.id.simpleButton)
    Button simpleButton;

    @BindView(R.id.simpleText)
    TextView simpleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        ButterKnife.bind(this);

        simpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleText.setText(R.string.new_text);
            }
        });
    }
}
