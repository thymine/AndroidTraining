package me.zhang.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> values;
    protected LayoutInflater inflater;

    public BaseListAdapter(Context context, List<T> values) {
        this.context = context;
        this.values = values;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        if (values != null)
            return values.size();
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (position > getCount() - 1 || values == null) {
            return null;
        }
        return values.get(position);
    }

}
