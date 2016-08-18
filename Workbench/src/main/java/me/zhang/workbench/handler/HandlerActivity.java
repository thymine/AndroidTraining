package me.zhang.workbench.handler;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 8/18/2016 2:54 PM.
 */
public class HandlerActivity extends AppCompatActivity implements WorkerThread.Callback {

    private static boolean isVisible;
    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    private LinearLayout mLeftSideLayout;
    private LinearLayout mRightSideLayout;
    private WorkerThread mWorkerThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        isVisible = true;
        mLeftSideLayout = (LinearLayout) findViewById(R.id.leftSideLayout);
        mRightSideLayout = (LinearLayout) findViewById(R.id.rightSideLayout);
        mWorkerThread = new WorkerThread(new Handler(), this);
        mWorkerThread.start();
        mWorkerThread.prepareHandler();
        Random random = new Random();
        for (String url : urls) {
            mWorkerThread.queueTask(url, random.nextInt(2), new ImageView(this));
        }
    }

    @Override
    protected void onPause() {
        isVisible = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWorkerThread.quit();
        super.onDestroy();
    }

    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap, int side) {
        imageView.setImageBitmap(bitmap);
        if (isVisible && side == LEFT_SIDE) {
            mLeftSideLayout.addView(imageView);
        } else if (isVisible && side == RIGHT_SIDE) {
            mRightSideLayout.addView(imageView);
        }
    }

    String[] urls = new String[]{
            "http://img5.duitang.com/uploads/blog/201405/10/20140510094144_kfLji.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201405/09/20140509201906_kERjy.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201405/08/20140508193226_UaSGB.thumb.jpeg",
            "http://img4.duitang.com/uploads/blog/201405/05/20140505201747_aPNtf.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201405/06/20140506104824_jPWQj.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201405/05/20140505201105_MkXdn.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201405/03/20140503205822_GCzta.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201405/03/20140503205535_cCHPB.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201405/03/20140503204354_xxSQX.thumb.jpeg",
            "http://img4.duitang.com/uploads/blog/201404/06/20140406191307_GTxFd.thumb.jpeg",
            "http://img4.duitang.com/uploads/blog/201404/06/20140406191247_BG2cU.thumb.jpeg",
            "http://img4.duitang.com/uploads/blog/201404/06/20140406191114_MzYtw.thumb.jpeg",
            "http://img4.duitang.com/uploads/blog/201404/06/20140406191127_fazJA.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201407/10/20140710081204_vYnCi.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201407/15/20140715133758_M2Y3J.thumb.jpeg",
            "http://img4.duitang.com/uploads/item/201407/13/20140713190806_TGJHm.thumb.jpeg",
            "http://img4.duitang.com/uploads/blog/201407/05/20140705223413_5r3ze.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201407/13/20140713012526_tcy5u.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201407/13/20140713121424_Gy43T.thumb.jpeg",
            "http://img4.duitang.com/uploads/item/201407/15/20140715033844_tcvrY.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201407/10/20140710181106_4HHay.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201407/06/20140706122850_8PER3.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201407/07/20140707192042_8xKXF.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201407/07/20140707063954_mVa3y.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201407/08/20140708093733_AFFhc.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201407/07/20140707161220_unvzn.thumb.jpeg",
            "http://img4.duitang.com/uploads/blog/201407/07/20140707113856_hBf3R.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201308/26/20130826090203_NtuYA.thumb.jpeg",
            "http://img4.duitang.com/uploads/item/201407/07/20140707145925_dHeKV.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201406/25/20140625101534_xiZxN.thumb.jpeg",
            "http://img5.duitang.com/uploads/blog/201406/30/20140630150534_EWUVY.thumb.jpeg"
    };

}
