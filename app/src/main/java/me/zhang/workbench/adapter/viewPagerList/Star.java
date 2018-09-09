package me.zhang.workbench.adapter.viewPagerList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zhang on 12/31/2016 9:40 AM.
 */
public class Star implements Serializable {

    private static final long serialVersionUID = 3336218515747332681L;

    String id;
    String name;

    List<Category> categories;

    public Star(String id, String name, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.categories = categories;
    }
}
