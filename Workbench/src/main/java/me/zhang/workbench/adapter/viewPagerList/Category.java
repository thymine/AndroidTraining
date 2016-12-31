package me.zhang.workbench.adapter.viewPagerList;

import java.io.Serializable;

/**
 * Created by Zhang on 12/31/2016 9:57 AM.
 */
public class Category implements Serializable {

    private static final long serialVersionUID = -5620706388930846800L;

    int categoryId;
    String categoryName;
    int categoryType;

    MetaData data;

    public Category(int categoryId, String categoryName, int categoryType, MetaData data) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.data = data;
    }
}
