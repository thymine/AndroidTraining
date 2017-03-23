package me.zhang.workbench.view;

import java.util.ArrayList;
import java.util.List;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2017/3/23.
 */
class DataSource {

    static List<CustomItem> buildCustomItmes(int numOfItems) {
        List<CustomItem> itemList = new ArrayList<>();
        for (int i = 0; i < numOfItems; i++) {
            CustomItem item = new CustomItem.Builder()
                    .icon(R.mipmap.ic_launcher)
                    .title("Title " + (i + 1))
                    .subtitle("Subtitle " + (i + 1))
                    .build();
            itemList.add(item);
        }
        return itemList;
    }

}
