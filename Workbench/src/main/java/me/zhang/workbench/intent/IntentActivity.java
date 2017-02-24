package me.zhang.workbench.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        ButterKnife.bind(this);
    }

    public void onClickButton(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.openAnonymousActivity:
                intent.setAction(Intent.ACTION_SEND);
                Uri uri = Uri.parse("workbench://main?")
                        .buildUpon()
                        .appendQueryParameter("text", "Magic Here?")
                        .build();
                intent.setData(uri);
                break;
            case R.id.setAlarm:
                intent.setAction(AlarmClock.ACTION_SET_ALARM)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, "Hi, clock!")
                        .putExtra(AlarmClock.EXTRA_HOUR, 13)
                        .putExtra(AlarmClock.EXTRA_MINUTES, 0);
                break;
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Activity Can Handle This.", Toast.LENGTH_SHORT).show();
        }
    }
}
