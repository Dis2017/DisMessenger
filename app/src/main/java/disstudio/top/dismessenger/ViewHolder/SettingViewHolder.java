package disstudio.top.dismessenger.ViewHolder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import disstudio.top.dismessenger.Item.SettingItem;
import disstudio.top.dismessenger.R;

public class SettingViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout mItemView;
    private ImageView mIcon;
    private TextView mItemTitle, mMessage, mTitle;

    public SettingViewHolder(@NonNull View itemView) {
        super(itemView);
        mIcon = itemView.findViewById(R.id.setting_item_icon);
        mItemView = itemView.findViewById(R.id.setting_item);
        mItemTitle = itemView.findViewById(R.id.setting_item_title);
        mTitle = itemView.findViewById(R.id.setting_title);
        mMessage = itemView.findViewById(R.id.setting_item_message);
    }

    public void bind(final SettingItem item) {
        if (item.getType() == SettingItem.TYPE_TITLE) {
            mItemView.setVisibility(View.GONE);
            mTitle.setText(item.getTitle());
            return;
        }
        mTitle.setVisibility(View.GONE);
        if (item.getAction() != null) {
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.getAction().run();
                }
            });
        }
        mIcon.setImageDrawable(item.getIcon());
        mItemTitle.setText(item.getTitle());
        switch (item.getType()) {
            case SettingItem.TYPE_MESSAGE: {
                mMessage.setText(item.getMessage());
                break;
            }
        }
    }


}
