package me.zhang.workbench.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.openAnonymousActivity)
    Button mOpenAnonymousActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        ButterKnife.bind(this);

        mOpenAnonymousActivityButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        Uri uri = Uri.parse("workbench://main?")
                .buildUpon()
                .appendQueryParameter("text", "Magic Here?")
                .build();
        intent.setData(uri);
        startActivity(intent);
    }
}
