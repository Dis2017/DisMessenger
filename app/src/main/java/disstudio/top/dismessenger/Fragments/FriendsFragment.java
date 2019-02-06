package disstudio.top.dismessenger.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import disstudio.top.dismessenger.Activity.NewFriendRequestListActivity;
import disstudio.top.dismessenger.Activity.SearchActivity;
import disstudio.top.dismessenger.Other.BaseFragment;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class FriendsFragment extends BaseFragment implements View.OnClickListener {

    private Handler mHandler = new Handler();
    private View mSearchBar, mNewFriend, mCreateGroupChat;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView mNewFriendNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        mSearchBar = view.findViewById(R.id.friends_search_bar);
        mNewFriend = view.findViewById(R.id.friends_new_friend);
        mCreateGroupChat = view.findViewById(R.id.friends_create_group_chat);
        mTabLayout = view.findViewById(R.id.friends_tab_layout);
        mViewPager = view.findViewById(R.id.friends_view_pager);
        mNewFriendNumber = view.findViewById(R.id.friends_new_friend_number);

        mSearchBar.setOnClickListener(this);
        mNewFriend.setOnClickListener(this);
        mCreateGroupChat.setOnClickListener(this);

        mTabLayout.setupWithViewPager(mViewPager);

        updateView();

        return view;
    }

    public void updateView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String[] requests = MyApplication.getAddFriendRequests();
                if (requests != null) {
                    mNewFriendNumber.setText("" + requests.length);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends_search_bar: {
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            }
            case R.id.friends_new_friend: {
                startActivity(new Intent(getContext(), NewFriendRequestListActivity.class));
                break;
            }
            case R.id.friends_create_group_chat: {
                break;
            }
        }
    }
}
