package me.zhang.workbench.memory;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhang.workbench.R;

public class MemoryManagement extends AppCompatActivity {

    @InjectView(R.id.heapSizeText)
    TextView mHeapSizeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_management);

        ButterKnife.inject(this);

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mHeapSizeText.setText(getString(R.string.heap_size, manager.getMemoryClass()));
    }
}
