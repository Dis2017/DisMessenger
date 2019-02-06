package disstudio.top.dismessenger.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private TextView mName, mMessage;
    private ImageView mHeadPortrait;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.message_item_name);
        mMessage = itemView.findViewById(R.id.message_item_message);
        mHeadPortrait = itemView.findViewById(R.id.message_item_head_portrait);
    }

    public void bind(User user) {
        mHeadPortrait.setImageBitmap(user.getHeadPortrait());
        mName.setText(user.getName());
    }

}
