package me.zhang.lab.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import me.zhang.lab.Images;
import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/9/6 下午 3:26 .
 */
public class HorizontalListActivity extends Activity implements CustomAdapter.OnImageItemClickListener {

    private static final String TAG = "HorizontalListActivity";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomAdapter adapter;
    private List<String> dataSet;

    private ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_horizontal_list);

        initDataSet();

        preview = (ImageView) findViewById(R.id.iv_preview);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CustomAdapter(this, dataSet, this);
        recyclerView.setAdapter(adapter);
    }

    private void initDataSet() {
        dataSet = Arrays.asList(Images.imageUrls);
    }

    @Override
    public void onImageItemClick(int index) {
        // preview selected image thumb
        Picasso.with(this).load(dataSet.get(index)).into(preview);

        Log.d(TAG, "onImageItemClick(" + index + ")");

    }
}
