package me.zhang.gradleforandroid.http;

import me.zhang.gradleforandroid.Grabber;

/**
 * Created by Zhang on 12/11/2016 2:03 PM.
 */
public class ContentGrabber implements Grabber {

    @Override
    public String grab() {
        return "User contents with pictures.";
    }

}
