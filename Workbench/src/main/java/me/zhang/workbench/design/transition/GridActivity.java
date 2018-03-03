package me.zhang.workbench.design.transition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class GridActivity extends AppCompatActivity {

    private final int[] colors = {DKGRAY, GRAY, LTGRAY, RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA};
    private final List<Integer> colorList = new ArrayList<>();

    {
        for (int i = 0; i < 30; i++) {
            colorList.add(colors[(int) (Math.random() * colors.length)]);
        }
    }

    @BindView(R.id.grid)
    RecyclerView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);

        grid.setHasFixedSize(true);
        grid.addItemDecoration(new GridMarginDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
        grid.setAdapter(new GridAdapter());
    }

    private class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.grid_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setBackgroundColor(colorList.get(position));
        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }

        class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GridActivity.this, DetailActivity.class);
                        intent.putExtra(getString(R.string.key_clicked_color), colorList.get(getAdapterPosition()));
                        startActivity(intent, makeSceneTransitionAnimation(GridActivity.this, v, getString(R.string.transition_name_hero)).toBundle());
                    }
                });
            }
        }

    }
}
