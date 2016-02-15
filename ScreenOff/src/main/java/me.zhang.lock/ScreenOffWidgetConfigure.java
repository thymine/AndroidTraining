package me.zhang.lock;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

/**
 * Created by Zhang on 2016/2/15 下午 2:20 .
 */
public class ScreenOffWidgetConfigure extends Activity {
    private int mAppWidgetId;
    private RadioGroup groupButtonStyle;
    private Button buttonEnsure;
    private RemoteViews views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configure);
        groupButtonStyle = (RadioGroup) findViewById(R.id.group_btn_style);
        buttonEnsure = (Button) findViewById(R.id.btn_ensure);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // Create an Intent to launch ScreenOffActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ScreenOffActivity.class), 0);
        views = new RemoteViews(ScreenOffWidgetConfigure.this.getPackageName(), R.layout.widget_screenoff);
        views.setOnClickPendingIntent(R.id.layout_screenoff, pendingIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        groupButtonStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_original:
                        setBackgroundResource(R.drawable.launcher);
                        break;
                    case R.id.btn_blue:
                        setBackgroundResource(R.drawable.launcher_blue);
                        break;
                    case R.id.btn_green:
                        setBackgroundResource(R.drawable.launcher_green);
                        break;
                }
            }
        });

        buttonEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 应用Widget样式
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ScreenOffWidgetConfigure.this);
                appWidgetManager.updateAppWidget(mAppWidgetId, views);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }

    private void setBackgroundResource(int background) {
        views.setInt(R.id.layout_screenoff, "setBackgroundResource", background);
    }

    @Override
    protected void onStop() {
        super.onStop();

        groupButtonStyle.setOnCheckedChangeListener(null);
    }
}
