package me.zhang.workbench.launchMode;

import android.content.Intent;
import android.view.View;

public class AlphaActivity extends BaseActivity {

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, BetaActivity.class));
    }

}
