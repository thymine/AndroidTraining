package me.zhang.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zhang on 6/9/2015 2:36 下午.
 */
public class OldAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Bean> mDatas;

    public OldAdapter(Context context, List<Bean> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_listview, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.id_title);
            holder.desc = (TextView) convertView.findViewById(R.id.id_desc);
            holder.time = (TextView) convertView.findViewById(R.id.id_time);
            holder.phone = (TextView) convertView.findViewById(R.id.id_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bean bean = mDatas.get(position);
        holder.title.setText(bean.getTitle());
        holder.desc.setText(bean.getDesc());
        holder.time.setText(bean.getTime());
        holder.phone.setText(bean.getPhone());

        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView desc;
        TextView time;
        TextView phone;
    }

}
