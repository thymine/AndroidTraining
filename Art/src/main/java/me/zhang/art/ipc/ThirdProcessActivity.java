package me.zhang.art.ipc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;
import static me.zhang.art.ipc.UserManager.sUserId;

/**
 * Created by Li on 6/9/2016 10:55 AM.
 */
public class ThirdProcessActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("ThirdProcessActivity");

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        TextView userIdText = new TextView(this);
        userIdText.setText(String.format(Locale.getDefault(), "User Id is %d", sUserId));
        userIdText.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        userIdText.setGravity(CENTER);
        userIdText.setTextSize(32);

        container.addView(userIdText);

        setContentView(container);
    }

}
