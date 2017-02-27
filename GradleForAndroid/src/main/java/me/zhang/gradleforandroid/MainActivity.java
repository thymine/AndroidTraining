package me.zhang.gradleforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhang.gradleforandroid.http.ContentGrabber;
import me.zhang.gradleforandroid.http.HttpApi;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.author)
    TextView mAuthor;

    @BindView(R.id.server)
    TextView mServer;

    @BindView(R.id.packageName)
    TextView mPackageName;

    @BindView(R.id.minSdkVersion)
    TextView mMinSdkVersion;

    @BindView(R.id.contents)
    TextView mContents;

    @BindView(R.id.login)
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuthor.setText(BuildConfig.AUTHOR);
        mServer.setText(HttpApi.SERVER);

        mPackageName.setText(getApplicationContext().getPackageName());
        mMinSdkVersion.setText(String.format(Locale.getDefault(), "targetSdkVersion: %d", getApplicationContext().getApplicationInfo().targetSdkVersion));

        Grabber contentGrabber = new ContentGrabber();
        mContents.setText(contentGrabber.grab());
    }

    @OnClick(R.id.login)
    public void login(View view) { // All arguments to the listener method are optional.
        startActivity(new Intent(this, LoginActivity.class));
    }

}
