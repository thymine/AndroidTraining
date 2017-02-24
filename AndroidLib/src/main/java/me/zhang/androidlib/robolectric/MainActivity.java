package me.zhang.androidlib.robolectric;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.androidlib.R;
import me.zhang.androidlib.R2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R2.id.button)
    Button mButton;

    @BindView(R2.id.text)
    TextView mTextView;

    int mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mButton.setOnClickListener(this);

        Uri uri = getIntent().getData();
        if (uri != null) {
            String text = uri.getQueryParameter("text");
            if (!TextUtils.isEmpty(text)) {
                mTextView.setText(text);
            }
        }
    }

    @Override
    public void onClick(View v) {
        mTextView.setText(String.format(Locale.getDefault(), "Magic: %d", ++mCounter));
    }
}
