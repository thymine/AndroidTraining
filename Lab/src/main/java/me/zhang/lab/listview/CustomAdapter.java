package me.zhang.lab.listview;

import android.content.Context;
import android.util.Log;
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

    private static final String TAG = "LVAdapter";

    private final List<Item> datas;
    private final Context context;

    public CustomAdapter(Context context, List<Item> datas) {
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
        ViewHolder holder;
        if (convertView == null) {
            Log.i("Zhang", "convertView == null");
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_custom, parent, false);
            convertView.setBackgroundResource(R.drawable.item_selector_background);

            holder.bigTitle = (TextView) convertView.findViewById(R.id.big_title);
            holder.dontClick = (Button) convertView.findViewById(R.id.dont_click);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Item item = datas.get(position);
        holder.bigTitle.setText(item.title);
        holder.dontClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Don't Click ~ " + item.title, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView bigTitle;
        Button dontClick;
    }

}
