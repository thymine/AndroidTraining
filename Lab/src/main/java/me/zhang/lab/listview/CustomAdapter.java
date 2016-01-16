package me.zhang.lab.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.zhang.lab.R;

/**
 * Created by Zhang on 8/6/2015 9:19 下午.
 */
public class CustomAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_0 = 0;
    private static final int VIEW_TYPE_1 = 1;

    private final List<Item> datas;
    private final Context context;

    public CustomAdapter(Context context, List<Item> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Item getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        Item item = datas.get(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_0:
                item.isEnabled = true;
                break;
            case VIEW_TYPE_1:
                item.isEnabled = false;
                break;
        }
        return datas.get(position).isEnabled;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 5 != 0 ? VIEW_TYPE_0 : VIEW_TYPE_1;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = datas.get(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_0:
                convertView = getViewType0(convertView, parent, item);
                break;
            case VIEW_TYPE_1:
                convertView = getViewType1(convertView, parent, item);
                break;
        }
        return convertView;
    }

    @NonNull
    private View getViewType1(View convertView, ViewGroup parent, Item item) {
        ViewHolder1 holder1;
        if (convertView == null) {
            holder1 = new ViewHolder1();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_custom1, parent, false);
            convertView.setBackgroundResource(R.drawable.item_selector_background1);

            holder1.evenBiggerTitle = (TextView) convertView.findViewById(R.id.even_bigger_title);

            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }
        holder1.evenBiggerTitle.setText(item.title);
        return convertView;
    }

    @NonNull
    private View getViewType0(View convertView, ViewGroup parent, final Item item) {
        ViewHolder0 holder;
        if (convertView == null) {
            holder = new ViewHolder0();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_custom, parent, false);
            convertView.setBackgroundResource(R.drawable.item_selector_background);

            holder.bigTitle = (TextView) convertView.findViewById(R.id.big_title);
            holder.dontClick = (Button) convertView.findViewById(R.id.dont_click);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder0) convertView.getTag();
        }
        holder.bigTitle.setText(item.title);
        holder.dontClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Don't Click ~ " + item.title, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    static class ViewHolder0 {
        TextView bigTitle;
        Button dontClick;
    }

    static class ViewHolder1 {
        TextView evenBiggerTitle;
    }

}
