package me.zhang.workbench.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2017/3/23.
 */
class CustomViewGroupAdapter extends RecyclerView.Adapter<CustomViewGroupAdapter.ViewHolder> {

    private List<CustomItem> mCustomItems;

    CustomViewGroupAdapter(List<CustomItem> customItems) {
        mCustomItems = customItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_custom_view_group, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo(getCustomItem(position));
    }

    /**
     * Retrive {@link CustomItem} by position
     *
     * @param position Item position
     * @return {@link CustomItem}
     */
    private CustomItem getCustomItem(int position) {
        return mCustomItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mCustomItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView mIcon;

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.subtitle)
        TextView mSubtitle;

        void bindTo(CustomItem item) {
            mIcon.setImageResource(item.getIcon());
            mTitle.setText(item.getTitle());
            mSubtitle.setText(item.getSubtitle());
        }

        ViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
