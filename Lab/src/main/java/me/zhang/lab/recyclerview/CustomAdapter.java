package me.zhang.lab.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.zhang.lab.R;
import me.zhang.lab.utils.DesignerUtils;

/**
 * Created by Zhang on 2015/9/8 上午 11:20 .
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";

    private Context context;

    private OnImageItemClickListener listener;
    private List<String> dataSet;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet List<Drawable> containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(Context context, List<String> dataSet, OnImageItemClickListener listener) {
        this.context = context;
        checkDataSet(dataSet);
        this.dataSet = dataSet;
        checkListener(listener);
        this.listener = listener;
    }

    private void checkListener(OnImageItemClickListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("listener should not be null");
    }

    private void checkDataSet(List<String> dataSet) {
        if (dataSet == null)
            throw new IllegalArgumentException("dataSet should not be null");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /* Get a new item view */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_column_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageItemClick(position);
            }
        });

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        int width = (int) DesignerUtils.dp2px(72, context);
        int height = (int) DesignerUtils.dp2px(72, context);
        Picasso
                .with(context)
                .load(dataSet.get(position)).resize(width, height)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.getImageView());

        // use position arg to indicate each item
        holder.getTextView().setText(Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface OnImageItemClickListener {
        void onImageItemClick(int index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            imageView = (ImageView) itemView.findViewById(R.id.iv_thumb);
            textView = (TextView) itemView.findViewById(R.id.tv_index);
        }

        public View getItemView() {
            return itemView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

}
