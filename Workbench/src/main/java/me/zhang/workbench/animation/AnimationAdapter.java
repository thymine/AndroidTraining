package me.zhang.workbench.animation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.zhang.workbench.R;

/**
 * Created by Li on 2016/10/15 17:04.
 */
class AnimationAdapter extends RecyclerView.Adapter<AnimationAdapter.AnimHolder> {

    private List<String> mColorNames;

    AnimationAdapter(List<String> colorNames) {
        this.mColorNames = colorNames;
    }

    @Override
    public AnimHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnimHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animation, parent, false));
    }

    @Override
    public void onBindViewHolder(AnimHolder holder, int position) {
        String colorName = mColorNames.get(position);
        holder.mAnimItemLayout.setBackgroundColor(RecyclerViewAnimationActivity.sColorNameMap.get(colorName));
        holder.mColorNameText.setText(colorName);
    }

    @Override
    public int getItemCount() {
        return mColorNames.size();
    }

    static class AnimHolder extends RecyclerView.ViewHolder {
        final View mAnimItemLayout;
        final TextView mColorNameText;

        AnimHolder(View itemView) {
            super(itemView);
            mAnimItemLayout = itemView.findViewById(R.id.animItemLayout);
            mColorNameText = (TextView) itemView.findViewById(R.id.colorNameText);
        }

    }


}
