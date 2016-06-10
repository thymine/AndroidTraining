package me.zhang.art.ipc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/9/2016 10:52 AM.
 */
public class DefaultProcessActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("DefaultProcessActivity");

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button openSecondProcessButton = new Button(this);
        openSecondProcessButton.setText("Open second process");
        openSecondProcessButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        openSecondProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DefaultProcessActivity.this, SecondProcessActivity.class));
            }
        });

        Button openThirdProcessButton = new Button(this);
        openThirdProcessButton.setText("Open third process");
        openThirdProcessButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        openThirdProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DefaultProcessActivity.this, ThirdProcessActivity.class));
            }
        });

        container.addView(openSecondProcessButton);
        container.addView(openThirdProcessButton);

        setContentView(container);
    }

}