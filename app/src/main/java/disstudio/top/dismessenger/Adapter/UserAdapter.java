package disstudio.top.dismessenger.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.R;
import disstudio.top.dismessenger.ViewHolder.UserViewHolder;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> mUserList;

    public UserAdapter(List<User> userList) {
        this.mUserList = userList;
    }
    public UserAdapter(User[] users) {
        this.mUserList = new ArrayList<>();
        for (User user : users) {
            if (user != null) {
                this.mUserList.add(user);
            }
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.bind(mUserList.get(i));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
