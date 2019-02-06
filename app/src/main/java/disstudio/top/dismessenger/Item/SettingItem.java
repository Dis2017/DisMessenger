package disstudio.top.dismessenger.Item;

import android.graphics.drawable.Drawable;

public class SettingItem {

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_SWITCH = 2;
    public static final int TYPE_TITLE = 3;

    private int mType;
    private Drawable mIcon;
    private String mTitle, mMessage;
    private Runnable mAction;

    public SettingItem(int type, Drawable icon, String title) {
        this.mType = type;
        this.mIcon = icon;
        this.mTitle = title;
    }

    public Runnable getAction() {
        return mAction;
    }

    public void setAction(Runnable action) {
        this.mAction = action;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
