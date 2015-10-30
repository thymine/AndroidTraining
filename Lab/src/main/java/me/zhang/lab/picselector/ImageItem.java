package me.zhang.lab.picselector;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Zhang on 2015/10/30 下午 2:25 .
 */
public class ImageItem implements Serializable {

    private static final long serialVersionUID = 5034370951972022046L;
    public String id;
    public Uri path;
    public boolean choose;

}
