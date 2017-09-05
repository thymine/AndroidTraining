package me.zhang.workbench.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 9/5/2017 7:33 PM.
 */
public class BorderImageView extends AppCompatImageView {

    private BorderDrawable mBorderDrawable;

    public BorderImageView(Context context) {
        this(context, null);
    }

    public BorderImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BorderImageView);
        int color = a.getColor(R.styleable.BorderImageView_border_color,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        int borderWidth = (int) a.getDimension(R.styleable.BorderImageView_border_width, 0);
        int borderInnerRadius = (int) a
                .getDimension(R.styleable.BorderImageView_border_inner_radius, 0);

        mBorderDrawable = new BorderDrawable(color, borderWidth, borderInnerRadius);

        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBorderDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBorderDrawable.draw(canvas);
    }

}
