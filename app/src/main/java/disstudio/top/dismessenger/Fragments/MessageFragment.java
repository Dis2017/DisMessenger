package disstudio.top.dismessenger.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import disstudio.top.dismessenger.Adapter.MessageAdapter;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Other.BaseFragment;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class MessageFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        List<User> list = new ArrayList<>();
//        for (int i = 0 ; i < 20 ; i++) {
//            list.add(MyApplication.getUserManager().getUser(i+""));
//        }
        mRecyclerView = view.findViewById(R.id.message_record_list);
        mRecyclerView.setAdapter(new MessageAdapter(list));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
