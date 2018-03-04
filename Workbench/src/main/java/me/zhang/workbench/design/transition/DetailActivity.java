package me.zhang.workbench.design.transition;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import me.zhang.workbench.R;
import me.zhang.workbench.utils.UiUtilsKt;

import static me.zhang.workbench.utils.ListUtils.Companion;

public class DetailActivity extends AppCompatActivity {

    private ViewPager colorsVP;
    private ArrayList<Integer> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNoActionBar_Details);
        setContentView(R.layout.activity_detail);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // tells the framework to wait until i tell it that its safe to create the animation.
            postponeEnterTransition();

            TransitionSet transitions = new TransitionSet();
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
            slide.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            transitions.addTransition(slide);

            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            transitions.addTransition(fade);

            getWindow().setEnterTransition(transitions);
        }

        colors = getIntent().getIntegerArrayListExtra(getString(R.string.key_color_list));
        colorsVP = findViewById(R.id.vp_colors);
        colorsVP.setPageMargin((int) UiUtilsKt.toPixel(4, this));
        colorsVP.setAdapter(new ColorsPagerAdapter(getSupportFragmentManager(), colors));

        final int index = getIntent().getIntExtra(getString(R.string.key_clicked_index), 0);
        // switch to specific page
        colorsVP.setCurrentItem(index);
    }

    public static class ColorFragment extends Fragment {

        private static final String KEY_COLOR = "key_color";
        private static final String KEY_POSITION = "key_position";

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

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            final int color = getArguments().getInt(KEY_COLOR, Color.WHITE);
            final View colorView = view.findViewById(R.id.v_color);
            colorView.setBackgroundColor(color);

            colorView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    colorView.getViewTreeObserver().removeOnPreDrawListener(this);
                    // now start the postponed enter transition
                    ActivityCompat.startPostponedEnterTransition(getActivity());
                    return true;
                }
            });

            // set transition name the same as the clicked source item.
            final int position = getArguments().getInt(KEY_POSITION, 0);
            ViewCompat.setTransitionName(colorView, getString(R.string.transition_name_hero, position));
        }
    }

    private static class ColorsPagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<Integer> colors;

        public ColorsPagerAdapter(FragmentManager fm, ArrayList<Integer> colors) {
            super(fm);
            this.colors = colors;
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
        public int getCount() {
            return Companion.getCount(colors);
        }
    }

}
