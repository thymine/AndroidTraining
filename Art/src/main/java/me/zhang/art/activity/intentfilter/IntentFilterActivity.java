package me.zhang.art.activity.intentfilter;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/8/2016 9:02 PM.
 */
public class IntentFilterActivity extends AppCompatActivity {

    private static final String ACTION_OPEN = "me.zhang.art.OPEN_ACTION";
    private static final String ACTION_ANOTHER = "me.zhang.art.ANOTHER_ACTION";
    private static final String CATEGORY_M = "me.zhang.art.category.M";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("IntentFilterActivity");

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button actionButton = new Button(this);
        actionButton.setText("action filter");
        actionButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACTION_OPEN));
            }
        });

        Button categoryButton = new Button(this);
        categoryButton.setText("category filter");
        categoryButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACTION_ANOTHER).addCategory(CATEGORY_M));
            }
        });

        Button dataButton = new Button(this);
        dataButton.setText("data filter");
        dataButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ACTION_OPEN).setDataAndType(Uri.parse("file://abc"), "image/png"));
            }
        });

        Button noButton = new Button(this);
        noButton.setText("no activity");
        noButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName componentName = new Intent(ACTION_OPEN).setDataAndType(Uri.parse("file://abc"), "image/png").resolveActivity(getPackageManager());
                if (componentName == null) {
                    Toast.makeText(IntentFilterActivity.this, "No Activity found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(IntentFilterActivity.this, componentName.getShortClassName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        container.addView(actionButton);
        container.addView(categoryButton);
        container.addView(dataButton);
        container.addView(noButton);

        setContentView(container);
    }
}
