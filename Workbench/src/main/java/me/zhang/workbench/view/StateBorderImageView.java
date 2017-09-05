package me.zhang.workbench.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 9/5/2017 7:33 PM.
 */
public class StateBorderImageView extends AppCompatImageView {

    private StateBorderDrawable mStateBorderDrawable;

    public StateBorderImageView(Context context) {
        this(context, null);
    }

    public StateBorderImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateBorderImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateBorderImageView);
        int colorDefault = a.getColor(R.styleable.StateBorderImageView_border_color_default,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        int colorPressed = a.getColor(R.styleable.StateBorderImageView_border_color_pressed,
                ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        int borderWidth = (int) a.getDimension(R.styleable.StateBorderImageView_border_width, 0);

        int[][] states = new int[][]{{-android.R.attr.state_pressed},
                {android.R.attr.state_pressed}};
        int[] colors = new int[]{colorDefault, colorPressed};

        int borderInnerRadius = (int) a
                .getDimension(R.styleable.StateBorderImageView_border_inner_radius, 0);

        mStateBorderDrawable = new StateBorderDrawable(new ColorStateList(states, colors),
                borderWidth, borderInnerRadius);
        mStateBorderDrawable.setCallback(this);

        a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        mStateBorderDrawable.setState(getDrawableState());
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable dr) {
        return super.verifyDrawable(dr) || dr == mStateBorderDrawable;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStateBorderDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mStateBorderDrawable.draw(canvas);
    }

}
