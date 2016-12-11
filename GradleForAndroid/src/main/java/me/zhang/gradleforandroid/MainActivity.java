package me.zhang.gradleforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.gradleforandroid.http.HttpApi;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.author)
    TextView mAuthor;

    @BindView(R.id.server)
    TextView mServer;

    @BindView(R.id.packageName)
    TextView mPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuthor.setText(BuildConfig.AUTHOR);
        mServer.setText(HttpApi.SERVER);

        mPackageName.setText(getApplicationContext().getPackageName());
    }
}
