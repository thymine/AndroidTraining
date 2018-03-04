package me.zhang.workbench.design.transition;

import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;
import static me.zhang.workbench.utils.IntentUtil.REQUEST_CODE;
import static me.zhang.workbench.utils.IntentUtil.SELECTED_ITEM_POSITION;

public class GridActivity extends AppCompatActivity {

    private static final String TAG = GridActivity.class.getSimpleName();
    private final int[] colors = {DKGRAY, GRAY, LTGRAY, RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA};
    private final ArrayList<Integer> colorList = new ArrayList<>();

    {
        for (int i = 0; i < 50; i++) {
            colorList.add(colors[(int) (Math.random() * colors.length)]);
        }
    }

    @BindView(R.id.grid)
    RecyclerView grid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);

        postponeEnterTransition();
        // Listener to reset shared element exit transition callbacks.
        getWindow().getSharedElementExitTransition().addListener(new TransitionCallback() {
            @Override
            public void onTransitionEnd(Transition transition) {
                setExitSharedElementCallback((SharedElementCallback) null);
            }
        });

        grid.setHasFixedSize(true);
        grid.addItemDecoration(new GridMarginDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
        grid.setAdapter(new GridAdapter());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        postponeEnterTransition();
        grid.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                grid.getViewTreeObserver().removeOnPreDrawListener(this);
                // Start the postponed transition when the recycler view is ready to be drawn.
                startPostponedEnterTransition();
                return true;
            }
        });

        if (data == null) return;

        final int selectedItem = data.getIntExtra(SELECTED_ITEM_POSITION, 0);
        grid.scrollToPosition(selectedItem);

        RecyclerView.ViewHolder holder = grid.findViewHolderForAdapterPosition(selectedItem);
        if (holder == null) {
            Log.w(TAG, "onActivityReenter: Holder is null, remapping cancelled.");
            return;
        }

        DetailSharedElementEnterCallback callback = new DetailSharedElementEnterCallback();
        callback.setColorView(holder.itemView);
        setExitSharedElementCallback(callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.grid_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setBackgroundColor(colorList.get(position));
            holder.itemView.setTransitionName(getString(R.string.transition_name_hero, position));
        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }

        class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GridActivity.this, DetailActivity.class);
                        intent.putIntegerArrayListExtra(getString(R.string.key_color_list), colorList);
                        intent.putExtra(getString(R.string.key_clicked_index), getAdapterPosition());

                        View decorView = getWindow().getDecorView();
                        View statusBackground = decorView.findViewById(android.R.id.statusBarBackground);
                        View navBackground = decorView.findViewById(android.R.id.navigationBarBackground);

                        Pair statusPair = Pair.create(statusBackground, statusBackground.getTransitionName());

                        final ActivityOptions options;
                        Pair photoPair = Pair.create(v, v.getTransitionName());
                        if (navBackground == null) {
                            options = ActivityOptions.makeSceneTransitionAnimation(GridActivity.this, photoPair, statusPair);
                        } else {
                            Pair navPair = Pair.create(navBackground, navBackground.getTransitionName());
                            options = ActivityOptions.makeSceneTransitionAnimation(GridActivity.this, photoPair, statusPair, navPair);
                        }

                        startActivityForResult(intent, REQUEST_CODE, options.toBundle());
                    }
                });
            }
        }

    }
}
