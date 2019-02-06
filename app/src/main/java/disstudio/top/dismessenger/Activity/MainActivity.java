package disstudio.top.dismessenger.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import disstudio.top.dismessenger.Adapter.MainViewPagerAdapter;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Dialog.InputDialog;
import disstudio.top.dismessenger.Dialog.PersonalDataDialog;
import disstudio.top.dismessenger.Network.Callback.getUserCallback;
import disstudio.top.dismessenger.Network.Callback.updateUserCallback;
import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;
import disstudio.top.dismessenger.View.UnslidableViewPager;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener, OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ConstraintLayout mCenterRoot;
    private UnslidableViewPager mUnslidableViewPager;
    private ImageView mHeadPortrait;
    private TextView mName, mSign;
    private User mCurrentUser;
    private MainViewPagerAdapter mMainViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyApplication.getCurrentUserAccount() == null) {
            startActivity(new Intent(this, LoginAndRegisterActivity.class));
            finish();
            return;
        }

        BottomNavigationView mBottomNavigationView;

        setContentView(R.layout.activity_main);

        //Find view
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.main_navigation_view);
        mCenterRoot = findViewById(R.id.main_center_root);
        mUnslidableViewPager = findViewById(R.id.main_view_pager);
        mBottomNavigationView = findViewById(R.id.main_bottom_navigation_view);

        //Set adapter
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mUnslidableViewPager.setAdapter(mMainViewPagerAdapter);

        //Set listener
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(this);

        //Init
        mBottomNavigationView.setSelectedItemId(R.id.main_bottom_navigation_message);
        mUnslidableViewPager.setOffscreenPageLimit(2);
        updateUserInfo();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void updateUserInfo() {
        //获取当前用户
        mCurrentUser = MyApplication.getCurrentUser();
        if (MyApplication.getCurrentUserAccount() == null) {
            MyApplication.showTipsDialog("登录账号为空");
        } else if (mCurrentUser == null) {
            String account = MyApplication.getCurrentUserAccount();
            MyApplication.getServer().getUserByAccount(new String[] {account}, new getUserCallback() {
                @Override
                public void onResult(boolean result, final User[] users) {
                    if (result && users.length > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyApplication.getUserManager().updateUser(users[0]);
                                updateUserInfo();
                            }
                        });
                    } else {
                        MyApplication.showTipsDialog("获取用户失败");
                    }
                }
            });
            return;
        }
        //设置ActionBar头像
        setLeftButton(new BitmapDrawable(mCurrentUser.getHeadPortrait()), new Runnable() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void run() {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        mNavigationView.post(new Runnable() {
            @Override
            public void run() {
                mHeadPortrait = findViewById(R.id.main_header_head_portrait);
                mName = findViewById(R.id.main_header_name);
                mSign = findViewById(R.id.main_header_sign);
                mHeadPortrait.setImageBitmap(mCurrentUser.getHeadPortrait());
                mName.setText(mCurrentUser.getName());
                mSign.setText(mCurrentUser.getSign());
                mSign.setOnClickListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.main_bottom_navigation_message: {
                mUnslidableViewPager.setCurrentItem(0);
                setTitle(item.getTitle());
                break;
            }
            case R.id.main_bottom_navigation_friends: {
                mUnslidableViewPager.setCurrentItem(1);
                setTitle(item.getTitle());
                break;
            }
            case R.id.main_bottom_navigation_dynamic: {
                mUnslidableViewPager.setCurrentItem(2);
                setTitle(item.getTitle());
                break;
            }

            case R.id.main_personal_data: {
                new PersonalDataDialog(this, mCurrentUser).show();
                break;
            }
            case R.id.main_setting: {
                startActivity(new Intent(this, SettingActivity.class));
                break;
            }
            case R.id.main_fast_file_transfer: {
                MyApplication.showTipsDialog("暂未开放");
                break;
            }
            case R.id.main_scan: {
                MyApplication.showTipsDialog("暂未开放");
                break;
            }
            case R.id.main_feedback: {
                MyApplication.showTipsDialog("暂未开放");
                break;
            }
            case R.id.main_help: {
                MyApplication.showTipsDialog("暂未开放");
                break;
            }
            case R.id.main_about: {
                MyApplication.showTipsDialog("暂未开放");
                break;
            }
        }

        layoutCenterRoot();
        return true;
    }

    @Override
    public void onCurrentUserUpdate() {
        super.onCurrentUserUpdate();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateUserInfo();
            }
        });
    }

    @Override
    public void onAddFriendRequestUpdate() {
        super.onAddFriendRequestUpdate();
        mMainViewPagerAdapter.onAddFriendRequestUpdate();
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        layoutCenterRoot();
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    private void layoutCenterRoot() {
        mCenterRoot.setTranslationX(mNavigationView.getRight());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_header_sign: {
                new InputDialog(this)
                        .setTitle("编辑签名")
                        .setHint("输入您的签名")
                        .setOnClickOkListener(new InputDialog.onClickOkListener() {
                            @Override
                            public boolean onClick(InputDialog dialog, String text) {
                                final String str = mCurrentUser.getSign();
                                mCurrentUser.setSign(text);
                                MyApplication.getServer().updateUser(mCurrentUser, new updateUserCallback() {
                                    @Override
                                    public void onResult(boolean result) {
                                        if (result) {
                                            MyApplication.getUserManager().updateUser(mCurrentUser);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    updateUserInfo();
                                                }
                                            });
                                        } else {
                                            mCurrentUser.setSign(str);
                                            MyApplication.showTipsDialog("提交失败");
                                        }
                                    }
                                });
                                return true;
                            }
                        }).show();

            }
        }
    }
}
