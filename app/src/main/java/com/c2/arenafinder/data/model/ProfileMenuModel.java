package com.c2.arenafinder.data.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class ProfileMenuModel {
    @DrawableRes
    private int icon;
    @StringRes
    private int itemTitle;
    @DrawableRes
    private int endIcon;

    public ProfileMenuModel(@DrawableRes int icon, @StringRes int itemTitle, @DrawableRes int endIcon) {
        this.icon = icon;
        this.itemTitle = itemTitle;
        this.endIcon = endIcon;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    @StringRes
    public int getItemTitle() {
        return itemTitle;
    }

    @DrawableRes
    public int getEndIcon() {
        return endIcon;
    }
}