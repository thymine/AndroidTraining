package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class TabLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tag_layout)
    TagLayout mTagLayout;

    private Random mRandom = new Random();
    private static final int mMinLength = 3;
    private static final int mMaxLength = 6;
    private static final int A = 65;
    private static final int Z = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.add: // Add new tag.
                addRandomTag();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addRandomTag() {
        Button child = new Button(this);
        child.setOnClickListener(this);
        child.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < (mMinLength + mRandom.nextInt(mMaxLength - mMinLength + 1)); i++) {
            builder.append(A + mRandom.nextInt(Z - A + 1));
        }
        String tagStr = builder.toString();

        child.setText(tagStr);
        mTagLayout.addView(child);
    }

    @Override
    public void onClick(View v) {
        mTagLayout.removeView(v);
    }

}
