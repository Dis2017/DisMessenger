package disstudio.top.dismessenger.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import disstudio.top.dismessenger.Adapter.SearchViewPagerAdapter;
import disstudio.top.dismessenger.Adapter.UserAdapter;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Callback.getUserCallback;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class SearchFindFragment extends SearchViewPagerAdapter.SearchFragment {

    private Handler mHandler = new Handler();

    private User[] mUsers;
    private View mDivider;
    private TextView mTips;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_find, container, false);
        mTips = view.findViewById(R.id.search_find_tips);
        mDivider = view.findViewById(R.id.search_find_divider);
        mRecyclerView = view.findViewById(R.id.search_find_recycler_view);

        mTips.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mDivider.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void updateUserBar(final boolean result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (result) {
                    mTips.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mDivider.setVisibility(View.VISIBLE);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mUserAdapter = new UserAdapter(mUsers);
                            mRecyclerView.setAdapter(mUserAdapter);
                        }
                    });
                } else {
                    mTips.setText(getString(R.string.not_find_user));
                }
            }
        });
    }

    @Override
    public void onSearch(String account) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTips.setVisibility(View.VISIBLE);
                mTips.setText(getString(R.string.wait));
                mRecyclerView.setVisibility(View.GONE);
                mDivider.setVisibility(View.GONE);
            }
        });
        MyApplication.getServer().getUserByAccount(new String[] {account}, new getUserCallback() {
            @Override
            public void onResult(boolean result, User[] users) {
                if (result && users != null && users.length > 0 && users[0] != null) {
                    mUsers = users;
                } else {
                    result = false;
                }
                updateUserBar(result);
            }
        });
    }

}
