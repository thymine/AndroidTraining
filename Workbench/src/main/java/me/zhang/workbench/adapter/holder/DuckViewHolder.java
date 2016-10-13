package me.zhang.workbench.adapter.holder;

import android.view.View;
import android.widget.TextView;

import me.zhang.workbench.R;
import me.zhang.workbench.adapter.contract.BaseViewHolder;
import me.zhang.workbench.adapter.contract.Heardable;

/**
 * Created by zhangxiangdong on 2016/10/13.
 */
public class DuckViewHolder extends BaseViewHolder {
    private final TextView mSoundText;

    public DuckViewHolder(View itemView) {
        super(itemView);

        mSoundText = (TextView) itemView.findViewById(R.id.soundText);
    }

    @Override
    public void bind(Heardable data) {
        mSoundText.setText(data.makeNoise());
    }
}
