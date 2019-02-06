package disstudio.top.dismessenger.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import disstudio.top.dismessenger.Adapter.UserAdapter;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Callback.getUserCallback;
import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class NewFriendRequestListActivity extends BaseActivity {

    private TextView mTips;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_request_list);

        mTips = findViewById(R.id.new_friend_tips);
        mRecyclerView = findViewById(R.id.new_friend_recycler_view);

        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTitle("新朋友");
        Drawable drawable = getResources().getDrawable(R.drawable.arrow_left);
        drawable.setTint(Color.WHITE);
        setLeftButton(drawable, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });

        updateView();
    }

    public void updateView() {
        String[] accounts = MyApplication.getAddFriendRequests();
        MyApplication.getServer().getUserByAccount(accounts, new getUserCallback() {
            @Override
            public void onResult(boolean result, final User[] users) {
                if (result) {
                    if (users.length == 0 || users[0] == null) {
                        setTips("空空如也");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (users.length != 0 && users[0] != null) {
                                mUserAdapter = new UserAdapter(users);
                                mRecyclerView.setAdapter(mUserAdapter);
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mTips.setVisibility(View.GONE);
                            } else {
                                mRecyclerView.setVisibility(View.GONE);
                                mTips.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    setTips("加载失败！");
                }
            }
        });
    }

    @Override
    public void onAddFriendRequestUpdate() {
        super.onAddFriendRequestUpdate();
        updateView();
    }

    public void setTips(final String tips) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTips.setText(tips);
            }
        });
    }
}
