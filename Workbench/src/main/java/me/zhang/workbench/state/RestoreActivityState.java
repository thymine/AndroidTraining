package me.zhang.workbench.state;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhang.workbench.R;

public class RestoreActivityState extends AppCompatActivity {

    public static final String INPUT_TEXT = "new_text";
    @InjectView(R.id.new_edittext)
    EditText mNewEditText;

    private String mInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_state);
        ButterKnife.inject(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mInputText = savedInstanceState.getString(INPUT_TEXT);
            showInputText();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INPUT_TEXT, mInputText);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    public void inputText(View view) {
        mInputText = mNewEditText.getText().toString();
        showInputText();
    }

    private void showInputText() {
        Toast.makeText(this, mInputText, Toast.LENGTH_SHORT).show();
    }
}