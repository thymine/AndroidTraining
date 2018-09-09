package me.zhang.workbench.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.zhang.workbench.R;

public class MyActivity extends AppCompatActivity {

    public static final String NAME_KEY = "name";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        editText = (EditText) findViewById(R.id.edit_text);
        Button helloButton = (Button) findViewById(R.id.hello_button);
        helloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello(v);
            }
        });
    }

    public void sayHello(View view) {
        String name = editText.getText().toString();
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(NAME_KEY, name);
        startActivity(intent);
    }
}
