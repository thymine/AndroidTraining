package me.zhang.lab.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

import me.zhang.lab.utils.HardwareUtils;

/**
 * PathEffect
 *
 * @author Aige
 * @since 2014/11/23
 */
@SuppressLint({ "NewApi", "DrawAllocation" })
public class PathEffectView extends View {
	private float mPhase;// 偏移值
	private Paint mPaint;// 画笔对象
	private Paint mTextPaint;// 画笔对象
	private Path mPath;// 路径对象
	private PathEffect[] mEffects;// 路径效果数组
	private String[] mTexts; // 路径描述
    private int[] dimens;

	public PathEffectView(Context context, AttributeSet attrs) {
		super(context, attrs);

        dimens = new int[2];
        HardwareUtils.getScreenDimens(context, dimens);
        /*
		 * 实例化画笔并设置属性
		 */
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.RED);
		mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);
		mPaint.setColor(Color.DKGRAY);

		// 实例化路径
		mPath = new Path();

		// 定义路径的起点
		mPath.moveTo(0, 0);

		// 定义路径的各个点
		for (int i = 0; i <= 30; i++) {
			mPath.lineTo(i * 35, (float) (Math.random() * 100));
		}

		// 创建路径效果数组
		mEffects = new PathEffect[7];
		mTexts = new String[7];
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/*
		 * 实例化各类特效
		 */
		mEffects[0] = null;
		mEffects[1] = new CornerPathEffect(10);
		mEffects[2] = new DiscretePathEffect(1.0F, 5.0F);
		mEffects[3] = new DashPathEffect(new float[] { 20, 10, 50, 5, 100, 30, 10, 5 }, mPhase);
		Path path = new Path();
		path.addCircle(0, 0, 3, Direction.CCW);
		mEffects[4] = new PathDashPathEffect(path, 12, mPhase, PathDashPathEffect.Style.ROTATE);
		mEffects[5] = new ComposePathEffect(mEffects[2], mEffects[4]);
		mEffects[6] = new SumPathEffect(mEffects[4], mEffects[3]);

		mTexts[0] = "PathEffect";
		mTexts[1] = "CornerPathEffect";
		mTexts[2] = "DiscretePathEffect";
		mTexts[3] = "DashPathEffect";
		mTexts[4] = "PathDashPathEffect";
		mTexts[5] = "ComposePathEffect";
		mTexts[6] = "SumPathEffect";

		/*
		 * 绘制路径
		 */
		for (int i = 0; i < mEffects.length; i++) {
			mPaint.setPathEffect(mEffects[i]);
			canvas.drawPath(mPath, mPaint);
			canvas.drawText(mTexts[i], dimens[0] / 2, 50, mTextPaint);

			// 每绘制一条将画布向下平移150个像素
			canvas.translate(0, 150);
		}

		// 刷新偏移值并重绘视图实现动画效果
		mPhase += 1;
		invalidate();
	}
}
