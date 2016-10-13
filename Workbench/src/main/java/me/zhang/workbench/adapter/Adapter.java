package me.zhang.workbench.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import me.zhang.workbench.adapter.contract.BaseViewHolder;
import me.zhang.workbench.adapter.contract.Heardable;
import me.zhang.workbench.adapter.contract.HolderFactory;

/**
 * Created by zhangxiangdong on 2016/10/12.
 */
public class Adapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Heardable> mModels;
    private LayoutInflater mInflater;
    private HolderFactory mFactory;
    private SparseArray<Heardable> mSparseArray;

    public Adapter(Context context, List<Heardable> models) {
        this.mModels = models;
        this.mInflater = LayoutInflater.from(context.getApplicationContext());
        this.mFactory = new HolderFactoryForList();
        this.mSparseArray = new SparseArray<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mSparseArray.get(viewType).holder(
                mInflater.inflate(viewType, parent, false), mFactory);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(mModels.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        Heardable heardable = mModels.get(position);
        int viewType = heardable.type(new TypeFactoryForList());
        mSparseArray.put(viewType, heardable);
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

}
