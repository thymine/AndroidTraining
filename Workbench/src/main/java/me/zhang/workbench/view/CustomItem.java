package me.zhang.workbench.view;

import java.util.UUID;

/**
 * Created by zhangxiangdong on 2017/3/23.
 */
class CustomItem {

    private String id;
    private int icon;
    private String title;
    private String subtitle;

    private CustomItem(Builder builder) {
        this.id = builder.id;
        this.icon = builder.icon;
        this.title = builder.title;
        this.subtitle = builder.subtitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public static class Builder {
        private String id;
        private int icon;
        private String title;
        private String subtitle;

        public Builder() {
            this.id = UUID.randomUUID().toString();
        }

        public Builder icon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder fromPrototype(CustomItem prototype) {
            id = prototype.id;
            icon = prototype.icon;
            title = prototype.title;
            subtitle = prototype.subtitle;
            return this;
        }

        public CustomItem build() {
            return new CustomItem(this);
        }
    }
}
