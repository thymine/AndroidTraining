package me.zhang.lab.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import me.zhang.lab.utils.MeasureUtil;

/**
 * EmbossMaskFilter
 *
 * @author Aige
 * @since 2014/11/23
 */
@SuppressLint("NewApi")
public class EmbossMaskFilterView extends View {
	private static final int H_COUNT = 2, V_COUNT = 4;// 水平和垂直切割数
	private Paint mPaint;// 画笔
	private PointF[] mPointFs;// 存储各个巧克力坐上坐标的点

	private int width, height;// 单个巧克力宽高
	private float coorY;// 单个巧克力坐上Y轴坐标值

	public EmbossMaskFilterView(Context context) {
		this(context, null);
	}

	public EmbossMaskFilterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 不使用硬件加速
		setLayerType(LAYER_TYPE_SOFTWARE, null);

		// 初始化画笔
		initPaint();

		// 计算参数
		cal(context);
	}

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		// 实例化画笔
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(0xFF603811);

		/**
		 * 数组必须要有而且只能有三个值即float[x,y,z]，这三个值代表了一个空间坐标系，我们的光照方向则由其定义，那么它是怎么定义的呢？首先x和y很好理解，
		 * 平面的两个维度嘛是吧，上面我们使用的是[1,1]也就是个45度角，而z轴表示光源是在屏幕后方还是屏幕前方，上面我们是用的是1，正值表示光源往屏幕外偏移1个单位，
		 * 负值表示往屏幕里面偏移，这么一说如果我把其值改为[1,1,-1]那么我们的巧克力朝着我们的一面应该就看不到了对吧，试试看撒~~~这个效果我就不截图了，
		 * 因为一片漆黑……但是你依然能够看到一点点灰度~就是因为我们的环境光ambient！，如果我们把值改为[1,1,2]往屏幕外偏移两个单位，那么我们巧克力正面光照将更强
		 */
		// 设置画笔遮罩滤镜
		mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1F }, 0.1F, 10F, 20F));
	}

	/**
	 * 计算参数
	 */
	private void cal(Context context) {
		int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

		width = screenSize[0] / H_COUNT;
		height = screenSize[1] / V_COUNT;

		int count = V_COUNT * H_COUNT;

		mPointFs = new PointF[count];
		for (int i = 0; i < count; i++) {
			if (i % 2 == 0) {
				coorY = i * height / 2F;
				mPointFs[i] = new PointF(0, coorY);
			} else {
				mPointFs[i] = new PointF(width, coorY);
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);

		// 画矩形
		for (int i = 0; i < V_COUNT * H_COUNT; i++) {
			canvas.drawRect(mPointFs[i].x, mPointFs[i].y, mPointFs[i].x + width, mPointFs[i].y + height, mPaint);
		}
	}
}
