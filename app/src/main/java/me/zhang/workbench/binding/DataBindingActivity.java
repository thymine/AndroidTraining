package me.zhang.workbench.binding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import me.zhang.workbench.databinding.ActivityDbBinding;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActivityDbBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_db);
        ActivityDbBinding binding = ActivityDbBinding.inflate(getLayoutInflater(),
                (ViewGroup) findViewById(android.R.id.content), true);
        binding.setUser(new User("Test", "User"));
    }
}
