package me.zhang.art.ipc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;
import static me.zhang.art.ipc.UserManager.sUserId;

/**
 * Created by Li on 6/9/2016 10:54 AM.
 */
public class SecondProcessActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("SecondProcessActivity");

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button openThirdProcessButton = new Button(this);
        openThirdProcessButton.setText("Open third process");
        openThirdProcessButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        openThirdProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondProcessActivity.this, ThirdProcessActivity.class));
            }
        });

        final TextView userIdText = new TextView(this);
        userIdText.setText(String.format(Locale.getDefault(), "User Id is %d", sUserId));
        userIdText.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        userIdText.setGravity(CENTER);
        userIdText.setTextSize(32);

        Button setUserIdButton = new Button(this);
        setUserIdButton.setText("Set User Id to 2");
        setUserIdButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        setUserIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.sUserId = 2;
                userIdText.setText(String.format(Locale.getDefault(), "User Id is %d", sUserId));
                Toast.makeText(SecondProcessActivity.this, "User Id is 2 now", Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(openThirdProcessButton);
        container.addView(setUserIdButton);
        container.addView(userIdText);

        setContentView(container);
    }

}
