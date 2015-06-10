package me.zhang.commonadapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import me.zhang.commonadapter.utils.CommonAdapter;
import me.zhang.commonadapter.utils.ViewHolder;

/**
 * Created by Zhang on 6/9/2015 3:42 下午.
 */
public class NewAdapter extends CommonAdapter<Bean> {

    public NewAdapter(Context context, List<Bean> datas) {
        super(context, datas, R.layout.item_listview);
    }

    @Override
    public void fillDatas(ViewHolder holder, Bean bean) {
        ((TextView) holder.getView(R.id.id_title)).setText(bean.getTitle());
        ((TextView) holder.getView(R.id.id_desc)).setText(bean.getDesc());
        ((TextView) holder.getView(R.id.id_time)).setText(bean.getTime());
        ((TextView) holder.getView(R.id.id_phone)).setText(bean.getPhone());
    }
}
