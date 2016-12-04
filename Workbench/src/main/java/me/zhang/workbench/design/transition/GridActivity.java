package me.zhang.workbench.design.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class GridActivity extends AppCompatActivity {

    @BindView(R.id.grid)
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);

        grid.setAdapter(new GridAdapter());
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(GridActivity.this, DetailActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(GridActivity.this
                                , view, "hero"
                        ).toBundle());
            }
        });
    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.grid_item, parent, false);
            }
            ((TextView) convertView).setText("A");
            return convertView;
        }
    }
}
