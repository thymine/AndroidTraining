package me.zhang.workbench.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.zhang.workbench.R;

public class LayoutAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);

        ListView listView = (ListView) findViewById(R.id.listView);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datas.add(String.valueOf(i + 1));
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas));

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.25f);
        controller.setOrder(LayoutAnimationController.ORDER_RANDOM);
        listView.setLayoutAnimation(controller);
    }
}
