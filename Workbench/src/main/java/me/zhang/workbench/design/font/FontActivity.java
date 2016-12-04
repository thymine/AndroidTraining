package me.zhang.workbench.design.font;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class FontActivity extends AppCompatActivity {

    @BindView(R.id.display4)
    TextView display4View;

    @BindView(R.id.display2)
    TextView display2View;

    @BindView(R.id.headline)
    TextView headlineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font);

        ButterKnife.bind(this);

        Typeface courgette = Typeface.createFromAsset(getAssets(), "Courgette-Regular.ttf");
        Typeface indieFlower = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");

        display4View.setTypeface(courgette);
        display2View.setTypeface(indieFlower);
        headlineView.setTypeface(courgette);
    }
}
