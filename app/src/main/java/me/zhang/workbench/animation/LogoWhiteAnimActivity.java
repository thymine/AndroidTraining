package me.zhang.workbench.animation;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import me.zhang.workbench.R;

public class LogoWhiteAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_white_anim);
    }

    public void animate(View view) {
        animate();
    }

    private void animate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImageView iv = (ImageView) findViewById(R.id.logo);
            AnimatedVectorDrawable logoAnim = (AnimatedVectorDrawable) getDrawable(R.drawable.io_logo_white_anim);
            iv.setImageDrawable(logoAnim);
            if (logoAnim != null) {
                logoAnim.start();
            }
        }
    }
}
