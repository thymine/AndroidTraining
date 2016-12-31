package me.zhang.workbench.adapter.viewPagerList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class ViewPagerListActivity extends AppCompatActivity {

    @BindView(R.id.viewPagerRecyclerView)
    RecyclerView mViewPagerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_list);
        ButterKnife.bind(this);

        List<Star> stars = new ArrayList<>();
        fillList(stars);

        mViewPagerRecyclerView.setHasFixedSize(true);
        mViewPagerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewPagerRecyclerView.setAdapter(new RecyclerAdapter(this, stars));
    }

    private void fillList(List<Star> stars) {
        Star alpha = getStarAlpha();
        Star beta = getStarBeta();
        Star gamma = getStarGamma();
        Star delta = getStarDelta();
        Star epsilon = getStarEpsilon();

        stars.add(alpha);
        stars.add(beta);
        stars.add(gamma);
        stars.add(delta);
        stars.add(epsilon);
    }

    @NonNull
    private Star getStarAlpha() {
        List<Category> categories = new ArrayList<>();
        MetaData data1 = new MetaData("Alpha1");
        Category category1 = new Category(1, "Alpha1", CategoryType.TYPE_ALBUM, data1);

        MetaData data2 = new MetaData("Alpha2");
        Category category2 = new Category(2, "Alpha2", CategoryType.TYPE_ALBUM, data2);

        MetaData data3 = new MetaData("Alpha3");
        Category category3 = new Category(3, "Alpha3", CategoryType.TYPE_VIDEO, data3);

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        return new Star("100", "Alpha", categories);
    }

    @NonNull
    public Star getStarBeta() {
        List<Category> categories = new ArrayList<>();
        MetaData data1 = new MetaData("Beta1");
        Category category1 = new Category(1, "Beta1", CategoryType.TYPE_ALBUM, data1);

        MetaData data2 = new MetaData("Beta2");
        Category category2 = new Category(2, "Beta2", CategoryType.TYPE_ALBUM, data2);

        MetaData data3 = new MetaData("Beta3");
        Category category3 = new Category(3, "Beta3", CategoryType.TYPE_VIDEO, data3);

        MetaData data4 = new MetaData("Beta4");
        Category category4 = new Category(4, "Beta4", CategoryType.TYPE_VIDEO, data4);

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);

        return new Star("101", "Beta", categories);
    }

    public Star getStarGamma() {
        List<Category> categories = new ArrayList<>();
        MetaData data1 = new MetaData("Gamma1");
        Category category1 = new Category(1, "Gamma1", CategoryType.TYPE_ALBUM, data1);

        MetaData data2 = new MetaData("Gamma2");
        Category category2 = new Category(2, "Gamma2", CategoryType.TYPE_ALBUM, data2);

        MetaData data3 = new MetaData("Gamma3");
        Category category3 = new Category(3, "Gamma3", CategoryType.TYPE_VIDEO, data3);

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        return new Star("102", "Gamma", categories);
    }

    public Star getStarDelta() {
        List<Category> categories = new ArrayList<>();
        MetaData data1 = new MetaData("Delta1");
        Category category1 = new Category(1, "Delta1", CategoryType.TYPE_ALBUM, data1);

        MetaData data2 = new MetaData("Delta2");
        Category category2 = new Category(2, "Delta2", CategoryType.TYPE_ALBUM, data2);

        MetaData data3 = new MetaData("Delta3");
        Category category3 = new Category(3, "Delta3", CategoryType.TYPE_VIDEO, data3);

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        return new Star("103", "Delta", categories);
    }

    public Star getStarEpsilon() {
        List<Category> categories = new ArrayList<>();
        MetaData data1 = new MetaData("Epsilon1");
        Category category1 = new Category(1, "Epsilon1", CategoryType.TYPE_ALBUM, data1);

        MetaData data2 = new MetaData("Epsilon2");
        Category category2 = new Category(2, "Epsilon2", CategoryType.TYPE_VIDEO, data2);

        categories.add(category1);
        categories.add(category2);

        return new Star("104", "Epsilon", categories);
    }

}
