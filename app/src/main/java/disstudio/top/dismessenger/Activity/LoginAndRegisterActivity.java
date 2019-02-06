package disstudio.top.dismessenger.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Callback.loginCallback;
import disstudio.top.dismessenger.Network.Callback.registerCallback;
import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class LoginAndRegisterActivity extends BaseActivity implements View.OnClickListener {

    public static final String NAME_MODE = "login register start mode";
    public static final int MODE_LOGIN = 0;
    public static final int MODE_REGISTER = 1;

    private TextView mChange;
    private Button mSubmit;
    private TextInputLayout mTextInputLayout1, mTextInputLayout2;
    private TextInputEditText mTextInputEditText1, mTextInputEditText2;

    private int mCurrentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

        mChange = findViewById(R.id.login_and_register_change_tv);
        mSubmit = findViewById(R.id.login_and_register_submit_btn);
        mTextInputEditText1 = findViewById(R.id.login_and_register_tet1);
        mTextInputLayout1 = findViewById(R.id.login_and_register_til1);
        mTextInputEditText2 = findViewById(R.id.login_and_register_tet2);
        mTextInputLayout2 = findViewById(R.id.login_and_register_til2);

        mChange.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        Intent intent = getIntent();
        mCurrentMode = intent.getIntExtra(NAME_MODE, MODE_LOGIN);

        if (savedInstanceState != null) {
            mTextInputEditText1.setText(savedInstanceState.getString("account"));
        }

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCurrentMode == MODE_LOGIN) {
            outState.putString("account", Objects.requireNonNull(mTextInputEditText1.getText()).toString());
        }
    }

    private void initView() {
        if (mCurrentMode == MODE_LOGIN) {
            mSubmit.setText(R.string.login);
            mChange.setText(R.string.register);
            mTextInputLayout1.setHint(getString(R.string.account));
            mTextInputLayout2.setHint(getString(R.string.password));
        } else {
            mSubmit.setText(R.string.register);
            mChange.setText(R.string.login);
            mTextInputLayout1.setHint(getString(R.string.password));
            mTextInputLayout2.setHint(getString(R.string.confirm_password));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_and_register_submit_btn: {
                final String text1 = Objects.requireNonNull(mTextInputEditText1.getText()).toString();
                final String text2 = Objects.requireNonNull(mTextInputEditText2.getText()).toString();
                //登录
                if (mCurrentMode == MODE_LOGIN) {
                    MyApplication.getServer().login(text1, text2, new loginCallback() {
                        @Override
                        public void onResult(boolean result, User user) {
                            if (result) {
                                MyApplication.setCurrentUser(text1, text2);
                                MyApplication.getUserManager().updateUser(user);
                                startActivity(new Intent(LoginAndRegisterActivity.this, MainActivity.class));
                                finish();
                            } else {
                                MyApplication.showTipsDialog("密码错误 ");
                            }
                        }
                    });
                }
                //注册
                else if (mCurrentMode == MODE_REGISTER) {
                    if (!text1.equals(text2)) {
                        MyApplication.showTipsDialog("两次密码不一致");
                        return;
                    }
                    MyApplication.getServer().register(text1, new registerCallback() {
                        @Override
                        public void onResult(boolean result, final User user) {
                            if (result) {
                                MyApplication.showTipsDialog("注册成功");
                                mCurrentMode = MODE_LOGIN;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTextInputEditText1.setText(user.getAccount());
                                        initView();
                                    }
                                });
                            } else {
                                MyApplication.showTipsDialog("注册失败");
                            }
                        }
                    });
                }
                break;
            }
            case R.id.login_and_register_change_tv: {
                mCurrentMode = mCurrentMode == MODE_LOGIN ? MODE_REGISTER : MODE_LOGIN;
                initView();
                break;
            }
        }
    }
}
