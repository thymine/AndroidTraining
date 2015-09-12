package me.zhang.lab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zhang on 8/6/2015 9:19 下午.
 */
public class LVAdapter extends BaseAdapter {

    private static final String TAG = "LVAdapter";

    private final List<Item> datas;
    private final Context context;
    private ViewHolder holder;

    public LVAdapter(Context context, List<Item> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount(...)");
        return datas.size();
    }

    @Override
    public Item getItem(int position) {
        Log.i(TAG, "getItem(...)");
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "getItemId(...)");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i(TAG, "getView(...)");

        convertView = initView(convertView, parent);

        Item item = datas.get(position);

        /* 加载数据 */
        fillData(holder, item);

//        if (position == 3) {
//            return null;
//        }

        return convertView;
    }

    @NonNull
    private View initView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            Log.i("Zhang", "convertView == null");

            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(me.zhang.lab.R.layout.item_list, parent, false);

            holder.tv_title = (TextView) convertView.findViewById(me.zhang.lab.R.id.tv_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private void fillData(ViewHolder holder, Item item) {
        holder.tv_title.setText(item.title);
    }

    class ViewHolder {
        TextView tv_title;
    }

}
