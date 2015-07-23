package com.soufun.eventbus;

/**
 * Created by Zhang on 2015/7/23 上午 9:09 .
 */
public class MessageEvent {

    private String message;

    public MessageEvent() {
    }

    public MessageEvent(String content) {
        this.message = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
