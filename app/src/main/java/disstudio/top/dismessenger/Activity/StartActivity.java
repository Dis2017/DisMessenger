package disstudio.top.dismessenger.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Callback.loginCallback;
import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class StartActivity extends BaseActivity {

    private TextView mTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mTips = findViewById(R.id.start_tips);
        MyApplication.getServer().start();
    }

    public void setTips(final String text, final int color, final Runnable action) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTips.setText(text);
                mTips.setTextColor(color);
                mTips.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (action != null) {
                            action.run();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onServerConnect() {
        super.onServerConnect();
        setTips("服务器连接成功", Color.GREEN, null);
        if (MyApplication.getCurrentUserAccount() != null && MyApplication.getCurrentUserPassword() != null) {
            setTips("正在自动登录...", Color.GREEN, null);
            MyApplication.getServer().login(MyApplication.getCurrentUserAccount(), MyApplication.getCurrentUserPassword(), new loginCallback() {
                @Override
                public void onResult(boolean result, User user) {
                    if (result) {
                        MyApplication.getUserManager().updateUser(user);
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                        finish();
                    } else {
                        setTips("自动登录失败", Color.RED, null);
                        startActivity(new Intent(StartActivity.this, LoginAndRegisterActivity.class));
                        finish();
                    }
                }
            });
        } else {
            setTips("跳转到登录", Color.GREEN, null);
            startActivity(new Intent(StartActivity.this, LoginAndRegisterActivity.class));
            finish();
        }
    }

    @Override
    public void onServerConnectFail() {
        super.onServerConnectFail();
        setTips("服务器连接失败（点击重连）", Color.RED, new Runnable() {
            @Override
            public void run() {
                setTips("正在重连...", Color.YELLOW, null);
                MyApplication.reconnectSerer();
            }
        });
    }
}
