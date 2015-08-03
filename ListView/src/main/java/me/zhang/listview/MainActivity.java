package me.zhang.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements LoadingMoreListView.LoadingMoreListener {

    private PullToRefreshListView lsPullToRefresh;

    /* 加载数据起始位置 */
    private int offset;
    /* 当前页，默认第一页 */
    private int currentPage = 1;

    private List<String> datas;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

    }

    private void initView() {
        lsPullToRefresh = (PullToRefreshListView) findViewById(R.id.lv_pull_to_refresh);
    }

    private void initData() {
        datas = new ArrayList<>();
        buildData();

        adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_expandable_list_item_1,
                        android.R.id.text1,
                        datas
                );

        lsPullToRefresh.setAdapter(adapter);
        lsPullToRefresh.setLoadingMoreListener(this);
    }

    @Override
    public void onLoad() {
        /* 请求下一页数据 */
        currentPage++;
        /* 获取更多数据 */
        buildData();

        /* 完成加载 */
        lsPullToRefresh.complete();

        /* 更新ListView */
        adapter.notifyDataSetChanged();
    }

    private void buildData() {
        int pageSize = 10;
        for (; offset < pageSize * currentPage; offset++) {
            datas.add(offset + 1 + "");
        }
    }
}
