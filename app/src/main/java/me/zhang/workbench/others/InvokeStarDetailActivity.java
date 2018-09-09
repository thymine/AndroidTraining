package me.zhang.workbench.others;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import me.zhang.workbench.R;

public class InvokeStarDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoke_star_detail);
    }

    public void invoke0(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("leso://star?from=superphone_04&jumpOutCommonParam=letvOutCommonParam&le_id=251&theme=0"));
        startActivity(intent);
    }

    public void invoke1(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("leso://star?from=superphone_04&jumpOutCommonParam=letvOutCommonParam&le_id=251&theme=1"));
        startActivity(intent);
    }
}
