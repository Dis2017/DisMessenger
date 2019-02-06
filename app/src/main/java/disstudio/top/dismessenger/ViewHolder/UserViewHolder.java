package disstudio.top.dismessenger.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Dialog.PersonalDataDialog;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private User mUser;
    private TextView mName, mSign;
    private ImageView mHeadPortrait;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.user_item_name);
        mSign = itemView.findViewById(R.id.user_item_sign);
        mHeadPortrait = itemView.findViewById(R.id.user_item_head_portrait);
        itemView.setOnClickListener(this);
    }

    public void bind(User user) {
        mUser = user;
        mName.setText(user.getName());
        mSign.setText(user.getSign());
        mHeadPortrait.setImageBitmap(user.getHeadPortrait());
    }

    @Override
    public void onClick(View v) {
        MyApplication.getForegroundActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new PersonalDataDialog(MyApplication.getForegroundActivity(), mUser).show();
            }
        });
    }

}
