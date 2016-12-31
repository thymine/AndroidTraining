package me.zhang.workbench.adapter.viewPagerList;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 12/31/2016 9:37 AM.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Star> mStarList;
    private FragmentActivity mActivity;

    public RecyclerAdapter(FragmentActivity activity, List<Star> stars) {
        mActivity = activity;
        mStarList = stars;
//        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view_pager, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mStarList.get(position));
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public int getItemCount() {
        if (mStarList == null) {
            return 0;
        }
        return mStarList.size();
    }

//    private ArrayMap<String, StarPagerAdapter> mAdapterMap = new ArrayMap<>();

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mStarName;
        PagerSlidingTabStrip mCategoryTabs;
        ViewPager mRecyclerPager;
        StarPagerAdapter adapter;

        void bind(Star star) {
            mStarName.setText(star.name);

//            StarPagerAdapter adapter = mAdapterMap.get(star.id);
//            if (adapter == null) {
//                adapter = new StarPagerAdapter(mActivity.getSupportFragmentManager(), star.categories);
//                mAdapterMap.put(star.id, adapter);
//            }
            if (adapter == null)
                adapter = new StarPagerAdapter(mActivity.getSupportFragmentManager(), star.categories);

            mRecyclerPager.setAdapter(adapter);
            mCategoryTabs.setViewPager(mRecyclerPager);
        }

        public ViewHolder(View itemView) {
            super(itemView);

            mStarName = (TextView) itemView.findViewById(R.id.starName);
            mCategoryTabs = (PagerSlidingTabStrip) itemView.findViewById(R.id.categoryTabs);
            mRecyclerPager = (ViewPager) itemView.findViewById(R.id.recyclerPager);
            mRecyclerPager.setId(generateViewId());
        }
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
