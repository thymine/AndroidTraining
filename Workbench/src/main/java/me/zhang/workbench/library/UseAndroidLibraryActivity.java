package me.zhang.workbench.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.icndb.JokeFinder;

import me.zhang.workbench.R;

public class UseAndroidLibraryActivity extends AppCompatActivity implements View.OnClickListener {

    private JokeFinder mJokeFinder;
    private TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_android_library);

        mJokeTextView = (TextView) findViewById(R.id.joke_text);
        mJokeTextView.setOnClickListener(this);
        mJokeFinder = new JokeFinder();
    }

    @Override
    public void onClick(View v) {
        getJoke();
    }

    private void getJoke() {
        mJokeFinder.getJoke(mJokeTextView, "Xavier", "Ducrohet");
    }

}
