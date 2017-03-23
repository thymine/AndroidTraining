package me.zhang.workbench.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2017/3/22.
 */
public class SimpleListItem extends ViewGroup {

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.title)
    TextView titleView;

    @BindView(R.id.subtitle)
    TextView subtitleView;

    public SimpleListItem(Context context) {
        this(context, null);
    }

    public SimpleListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Setup initial constraints.
        int widthUsed = 0;
        int heightUsed = 0;
        int width = getPaddingLeft() + getPaddingRight();
        int height = getPaddingTop() + getPaddingBottom();

        // Measure icon.
        measureChildWithMargins(icon, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);

        // Update the constraints.
        MarginLayoutParams lpI = (MarginLayoutParams) icon.getLayoutParams();
        widthUsed += icon.getMeasuredWidth() + (lpI.leftMargin + lpI.rightMargin);
        width += icon.getMeasuredWidth() + (lpI.leftMargin + lpI.rightMargin);

        // Measure title
        measureChildWithMargins(titleView, widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed);

        // Update the constraints.
        MarginLayoutParams lpT = (MarginLayoutParams) titleView.getLayoutParams();
        heightUsed += titleView.getMeasuredHeight() + (lpT.topMargin + lpT.bottomMargin);

        // Measure subtitle
        measureChildWithMargins(subtitleView, widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed);

        // Update the constraints.
        MarginLayoutParams lpS = (MarginLayoutParams) subtitleView.getLayoutParams();
        int tWidth = lpT.leftMargin + titleView.getMeasuredWidth() + lpT.rightMargin;
        int sWidth = lpS.leftMargin + subtitleView.getMeasuredWidth() + lpS.rightMargin;
        if (tWidth > sWidth) { // Title is wider than subtitle.
            width += tWidth;
        } else {
            width += sWidth;
        }
        height += Math.max(lpI.topMargin + icon.getMeasuredHeight() + lpI.bottomMargin,
                lpT.topMargin + titleView.getMeasuredHeight() + lpT.bottomMargin
                        + lpS.topMargin + subtitleView.getMeasuredHeight() + lpS.bottomMargin);

        // Set the dimension for this ViewGroup.
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) icon.getLayoutParams();

        // Figure out the x-coordinate and y-coordinate of the icon.
        int x = getPaddingLeft() + lp.leftMargin;
        int y = getPaddingTop() + lp.topMargin;

        // Layout the icon.
        icon.layout(x, y, x + icon.getMeasuredWidth(), y + icon.getMeasuredHeight());

        // Calculate the x-coordinate of the title: icon's right coordinate +
        // the icon's right margin.
        x += icon.getMeasuredWidth() + lp.rightMargin;

        // Add in the title's left margin.
        lp = (MarginLayoutParams) titleView.getLayoutParams();
        x += lp.leftMargin;

        // Calculate the y-coordinate of the title: this ViewGroup's top padding +
        // the title's top margin
        y = getPaddingTop() + lp.topMargin;

        // Layout the title.
        titleView.layout(x, y, x + titleView.getMeasuredWidth(), y + titleView.getMeasuredHeight());

        // The subtitle has the same x-coordinate as the title. So no more calculating there.

        // Calculate the y-coordinate of the subtitle: the title's bottom coordinate +
        // the title's bottom margin.
        y += titleView.getMeasuredHeight() + lp.bottomMargin;

        // Layout the subtitle.
        subtitleView.layout(x, y, x + subtitleView.getMeasuredWidth(), y + subtitleView.getMeasuredHeight());
    }

    /**
     * Validates if a set of layout parameters is valid for a child this ViewGroup.
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    /**
     * @return A set of default layout parameters when given a child with no layout parameters.
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        //noinspection ResourceType
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * @return A set of layout parameters created from attributes passed in XML.
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * Called when {@link #checkLayoutParams(LayoutParams)} fails.
     *
     * @return A set of valid layout parameters for this ViewGroup that copies appropriate/valid
     * attributes from the supplied, not-so-good-parameters.
     */
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

}
