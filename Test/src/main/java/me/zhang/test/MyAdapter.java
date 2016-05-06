package me.zhang.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zhang on 2016/5/6 下午 5:40 .
 */
public class MyAdapter extends BaseListAdapter<Data> {

    public MyAdapter(Context context, List<Data> values) {
        super(context, values);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindData(getItem(position));
        return convertView;
    }

    static class ViewHolder {
        TextView descText;

        public ViewHolder(View convertView) {
            descText = (TextView) convertView.findViewById(R.id.descText);
        }

        public void bindData(Data item) {
            descText.setText(String.format("%s", item.desc));
        }
    }

}
