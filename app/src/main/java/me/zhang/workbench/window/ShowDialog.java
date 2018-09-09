package me.zhang.workbench.window;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import me.zhang.workbench.R;

public class ShowDialog extends AppCompatActivity {

    private Dialog mActivityDialog;
    private Dialog mApplicationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dialog);

        mActivityDialog = new Dialog(this, 0);
        mActivityDialog.setContentView(R.layout.dialog_content);

        mApplicationDialog = new Dialog(getApplicationContext(), 0);
        mApplicationDialog.setContentView(R.layout.dialog_content);
        mApplicationDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
    }

    public void showActivityDialog(View view) {
        mActivityDialog.show();
    }

    public void showApplicationDialog(View view) {
        mApplicationDialog.show();
    }
}
