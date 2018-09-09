package me.zhang.workbench.design.transition;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import me.zhang.workbench.R;
import me.zhang.workbench.utils.UiUtilsKt;

import static me.zhang.workbench.utils.IntentUtil.SELECTED_ITEM_POSITION;
import static me.zhang.workbench.utils.ListUtils.Companion;

public class DetailActivity extends AppCompatActivity {

    private ViewPager colorsViewPager;
    private int initialIndex;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNoActionBar_Details);
        setContentView(R.layout.activity_detail);

        // tells the framework to wait until i tell it that its safe to create the animation.
        postponeEnterTransition();

        TransitionSet transitions = new TransitionSet();
        final Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
        slide.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        final Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        transitions.addTransition(slide);
        transitions.addTransition(fade);
        getWindow().setEnterTransition(transitions);

        final DetailSharedElementEnterCallback sharedElementEnterCallback = new DetailSharedElementEnterCallback();
        setEnterSharedElementCallback(sharedElementEnterCallback);

        colorsViewPager = findViewById(R.id.vp_colors);
        colorsViewPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (colorsViewPager.getChildCount() > 0) {
                    colorsViewPager.removeOnLayoutChangeListener(this);
                    // now start the postponed enter transition
                    startPostponedEnterTransition();
                }
            }
        });
        colorsViewPager.setPageMargin((int) UiUtilsKt.dp(4));
        colorsViewPager.setAdapter(new ColorsPagerAdapter(
                getSupportFragmentManager(),
                getIntent().getIntegerArrayListExtra(getString(R.string.key_color_list)),
                sharedElementEnterCallback
        ));
        initialIndex = getIntent().getIntExtra(getString(R.string.key_clicked_index), 0);
        // switch to specific page
        colorsViewPager.setCurrentItem(initialIndex);
    }

    @Override
    public void onBackPressed() {
        setActivityResult();
        super.onBackPressed();
    }

    @Override
    public void finishAfterTransition() {
        setActivityResult();
        super.finishAfterTransition();
    }

    private void setActivityResult() {
        final int currentItem = colorsViewPager.getCurrentItem();
        if (initialIndex == currentItem) {
            setResult(RESULT_OK);
        } else {
            Intent intent = new Intent();
            intent.putExtra(SELECTED_ITEM_POSITION, currentItem);
            setResult(RESULT_OK, intent);
        }
    }

    public static class ColorFragment extends Fragment {

        private static final String KEY_COLOR = "key_color";
        private static final String KEY_POSITION = "key_position";
        private View colorView;

        public static ColorFragment newInstance(int color, int position) {
            Bundle args = new Bundle();
            args.putInt(KEY_COLOR, color);
            args.putInt(KEY_POSITION, position);
            ColorFragment fragment = new ColorFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.pager_detail, container, false);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            final int color = getArguments().getInt(KEY_COLOR, Color.WHITE);
            colorView = view.findViewById(R.id.v_color);
            colorView.setBackgroundColor(color);

            // set transition name the same as the clicked source item.
            final int position = getArguments().getInt(KEY_POSITION, 0);
            colorView.setTransitionName(getString(R.string.transition_name_hero, position));
        }

        public View getColorView() {
            return colorView;
        }

    }

    private static class ColorsPagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<Integer> colors;
        private final DetailSharedElementEnterCallback sharedElementEnterCallback;

        public ColorsPagerAdapter(FragmentManager fm, ArrayList<Integer> colors,
                                  DetailSharedElementEnterCallback sharedElementEnterCallback) {
            super(fm);
            this.colors = colors;
            this.sharedElementEnterCallback = sharedElementEnterCallback;
        }

        @Override
        public Fragment getItem(int position) {
            Integer item = Companion.getItem(colors, position);
            int color = Color.WHITE; // use default color if item is null
            if (item != null) {
                color = item;
            }
            return ColorFragment.newInstance(color, position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorFragment fragment = (ColorFragment) object;
                sharedElementEnterCallback.setColorView(fragment.getColorView());
            }
        }

        @Override
        public int getCount() {
            return Companion.getCount(colors);
        }
    }

}
