package me.zhang.workbench.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import me.zhang.workbench.utils.UiUtils;

/**
 * Created by zhangxiangdong on 2017/8/23.
 * <p>
 * https://stackoverflow.com/questions/22943091/invert-paint-color-based-on-background
 */
public class TextProgressBar extends ProgressBar {
    private Paint pWhiteFill = new Paint();
    private Paint pBlackFill = new Paint();

    private Paint pWhiteTxtM = new Paint();
    private Paint pBlackTxtM = new Paint();

    private Rect mBlackRect = new Rect();
    private Rect mWhiteRect = new Rect();

    {
        pWhiteFill.setColor(Color.WHITE);
        pBlackFill.setColor(Color.BLACK);

        pWhiteTxtM.setAntiAlias(true);
        pWhiteTxtM.setTextSize(UiUtils.convertDpToPixel(18, getContext()));
        pWhiteTxtM.setColor(Color.WHITE);

        pBlackTxtM.setAntiAlias(true);
        pBlackTxtM.setTextSize(UiUtils.convertDpToPixel(18, getContext()));
        pBlackTxtM.setColor(Color.BLACK);
    }

    public TextProgressBar(Context context) {
        this(context, null);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // store the state of the canvas, so we can restore any previous clipping
        canvas.save();

        // note that it's a bad idea to create the Rect during the drawing operation,
        // better do that only once in advance also note that it might be sufficient
        // and faster to draw only the white part of the bar
        mWhiteRect.set(1, 1, getWidth() - 1, getHeight() - 1);
        canvas.drawRect(mWhiteRect, pWhiteFill);

        // this Rect should be created when the progress is set, not on every drawing operation
        int progressWidth = (int) ((1.0f * getProgress() / getMax()) * getWidth());
        mBlackRect.set(1, 1, progressWidth, getHeight() - 1);
        canvas.drawRect(mBlackRect, pBlackFill);

        // set the clipping region to the black part of the bar
        String text = String.valueOf(getProgress()) + "%";
        canvas.clipRect(mBlackRect);

        // calculate text position inside progress bar
        float textWidth = pWhiteTxtM.measureText(text);
        int xPosition = (int) (getWidth() / 2 - textWidth / 2);
        float textHeight = (pWhiteTxtM.descent() + pWhiteTxtM.ascent()) / 2;
        int yPosition = (int) (getHeight() / 2 - textHeight / 2);

        // draw the text using white ink
        canvas.drawText(text, xPosition, yPosition, pWhiteTxtM);

        // setting the clipping region to the complementary part of the bar
        canvas.clipRect(mWhiteRect, Region.Op.XOR);
        // draw the same text again using black ink
        canvas.drawText(text, xPosition, yPosition, pBlackTxtM);

        canvas.restore();
    }

}
