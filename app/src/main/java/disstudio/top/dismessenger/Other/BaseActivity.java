package disstudio.top.dismessenger.Other;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import disstudio.top.dismessenger.R;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private static String[] PERMISSIONS = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private ImageView mActionbarLeftBtn, mActionbarRightBtn;
    private TextView mActionbarTitle, mActionbarSubTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this,  PERMISSIONS, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyApplication.registerActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mActionbarTitle = findViewById(R.id.custom_action_bar_title);
        mActionbarSubTitle = findViewById(R.id.custom_action_bar_sub_title);
        mActionbarLeftBtn = findViewById(R.id.custom_action_bar_left_btn);
        mActionbarRightBtn = findViewById(R.id.custom_action_bar_right_btn);
    }

    @Override
    protected void onDestroy() {
        MyApplication.unRegisterActivity(this);
        super.onDestroy();
    }

    public void onServerConnect(){

    }
    public void onServerConnectFail(){}
    public void onCurrentUserUpdate(){}
    public void onAddFriendRequestUpdate(){}

    //设置标题
    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mActionbarTitle != null) {
            mActionbarTitle.setText(titleId);
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mActionbarTitle != null) {
            mActionbarTitle.setText(title);
        }
    }

    public void setSubTitle(int titleId) {
        if (mActionbarSubTitle != null) {
            mActionbarSubTitle.setText(titleId);
        }
    }
    public void setSubTitle(CharSequence title) {
        if (mActionbarSubTitle != null) {
            mActionbarSubTitle.setText(title);
        }
    }

    //设置左按钮
    public void setLeftButton(Drawable drawable, final Runnable runnable) {
        if (mActionbarLeftBtn == null) {
            return;
        }
        mActionbarLeftBtn.setImageDrawable(drawable);
        mActionbarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runnable.run();
            }
        });
    }

    //设置右按钮
    public void setRightButton(Drawable drawable, final Runnable runnable) {
        if (mActionbarRightBtn == null) {
            return;
        }
        mActionbarRightBtn.setImageDrawable(drawable);
        mActionbarRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runnable.run();
            }
        });
    }

}
