package disstudio.top.dismessenger.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.R;
import disstudio.top.dismessenger.ViewHolder.MessageViewHolder;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<User> mList;

    public MessageAdapter(List<User> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message,viewGroup, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        messageViewHolder.bind(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
