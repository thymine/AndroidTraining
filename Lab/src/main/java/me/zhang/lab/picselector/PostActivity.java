package me.zhang.lab.picselector;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/10/29 上午 9:54 .
 */
public class PostActivity extends BaseActivity {

    private static final String TAG = "PostActivity";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Toolbar is defined in the layout file
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_container, PostFragment.newInstance(null));
        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    // Handle post button click callback
    public void doPost(View view) {
        Log.i(TAG, "Posting from onClick...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Create your own menu
        inflater.inflate(R.menu.menu_post, menu);

        MenuItem postItem = menu.findItem(R.id.action_post);
        View postView = postItem.getActionView();
        Button postButton = (Button) postView.findViewById(R.id.btn_post);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Posting from ActionView...");
            }
        });

        return true;
    }
}
