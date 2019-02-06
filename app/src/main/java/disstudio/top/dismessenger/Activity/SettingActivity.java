package disstudio.top.dismessenger.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import disstudio.top.dismessenger.Adapter.SettingAdapter;
import disstudio.top.dismessenger.Item.SettingItem;
import disstudio.top.dismessenger.Network.Callback.logoutCallback;
import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class SettingActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("设置");
        Drawable drawable = getResources().getDrawable(R.drawable.arrow_left);
        drawable.setTint(Color.WHITE);
        setLeftButton(drawable, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
        mRecyclerView = findViewById(R.id.setting_recycler_view);
        initView();
    }

    private List<SettingItem> getOptions() {
        List<SettingItem> options = new ArrayList<>();
        SettingItem item;

        item = new SettingItem(SettingItem.TYPE_TITLE, null, "聊天");
        options.add(item);
        item = new SettingItem(SettingItem.TYPE_EMPTY, getResources().getDrawable(R.drawable.excuse_me), "免打扰");
        options.add(item);
        item = new SettingItem(SettingItem.TYPE_EMPTY, getResources().getDrawable(R.drawable.remind), "提醒");
        options.add(item);
        item = new SettingItem(SettingItem.TYPE_EMPTY, getResources().getDrawable(R.drawable.message_record), "消息记录");
        options.add(item);

        item = new SettingItem(SettingItem.TYPE_TITLE, null, "控制");
        options.add(item);
        item = new SettingItem(SettingItem.TYPE_EMPTY, getResources().getDrawable(R.drawable.logout), "退出登录");
        item.setAction(new Runnable() {
            @Override
            public void run() {
                MyApplication.getServer().logout(new logoutCallback() {
                    @Override
                    public void onResult(boolean result) {
                        if (result) {
                            //结束主界面
                            MainActivity activity = MyApplication.getMainActivity();
                            if (activity != null) {
                                activity.finish();
                            }
                            //清空当前用户
                            MyApplication.setCurrentUser(null, null);
                            startActivity(new Intent(SettingActivity.this, LoginAndRegisterActivity.class));
                            finish();
                        } else {
                            MyApplication.showTipsDialog("退出登录失败");
                        }
                    }
                });
            }
        });
        options.add(item);
        item = new SettingItem(SettingItem.TYPE_EMPTY, getResources().getDrawable(R.drawable.exit_app), "退出应用");
        item.setAction(new Runnable() {
            @Override
            public void run() {
                MyApplication.finish();
            }
        });
        options.add(item);

        return options;
    }

    private void initView() {
        final List<SettingItem> options = getOptions();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(new SettingAdapter(options));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
            }
        });
    }

}
