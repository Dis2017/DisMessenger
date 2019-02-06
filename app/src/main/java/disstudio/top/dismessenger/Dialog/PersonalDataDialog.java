package disstudio.top.dismessenger.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Objects;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Callback.agreeAddFriendRequest;
import disstudio.top.dismessenger.Network.Callback.refuseAddFriendRequest;
import disstudio.top.dismessenger.Network.Callback.requestAddFriendCallback;
import disstudio.top.dismessenger.Network.Callback.updateUserCallback;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class PersonalDataDialog extends Dialog implements View.OnClickListener {

    private User mUser;
    private Button mAddFriend, mAgree, mRefuse, mClose;
    private ImageView mHeadPortrait;
    private TextView mName, mAccount, mSign, mSex, mAge, mBirthday, mConstellation, mOccupation, mLocation, mHometown;
    private Handler mHandler;

    public PersonalDataDialog(@NonNull Context context, User user) {
        super(context, R.style.TranslucentDialogTheme);
        setContentView(R.layout.dialog_personal_data);

        mHandler = new Handler();
        mUser = user;
        mHeadPortrait = findViewById(R.id.personal_data_head_portrait);
        mName = findViewById(R.id.personal_data_name);
        mAccount = findViewById(R.id.personal_data_account);
        mSign = findViewById(R.id.personal_data_sign);
        mSex = findViewById(R.id.personal_data_sex);
        mAge = findViewById(R.id.personal_data_age);
        mBirthday = findViewById(R.id.personal_data_birthday);
        mConstellation = findViewById(R.id.personal_data_constellation);
        mOccupation = findViewById(R.id.personal_data_occupation);
        mLocation = findViewById(R.id.personal_data_location);
        mHometown = findViewById(R.id.personal_data_hometown);
        mAddFriend = findViewById(R.id.personal_data_add_friend);
        mAgree = findViewById(R.id.personal_data_agree_request);
        mRefuse = findViewById(R.id.personal_data_refuse_request);
        mClose = findViewById(R.id.personal_data_close);

        mHeadPortrait.setOnClickListener(this);
        mName.setOnClickListener(this);
        mSign.setOnClickListener(this);
        mSex.setOnClickListener(this);
        mBirthday.setOnClickListener(this);
        mOccupation.setOnClickListener(this);
        mLocation.setOnClickListener(this);
        mHometown.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mRefuse.setOnClickListener(this);
        mAgree.setOnClickListener(this);
        mAddFriend.setOnClickListener(this);
        if (user != null) {
            initUserInfo();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initUserInfo() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHeadPortrait.setImageBitmap(mUser.getHeadPortrait());
                mName.setText(mUser.getName());
                mAccount.setText(mUser.getAccount());
                mSign.setText(mUser.getSign());
                mSex.setText(mUser.getSex());
                mAge.setText(mUser.getAge() + "岁");
                mBirthday.setText(DateFormat.getDateInstance().format(mUser.getBirthday()));
                mConstellation.setText(mUser.getConstellation());
                mOccupation.setText(mUser.getOccupation());
                mLocation.setText(mUser.getLocation());
                mHometown.setText(mUser.getHometown());
                mAddFriend.setVisibility(MyApplication.getCurrentUserAccount().equals(mUser.getAccount()) ? View.GONE : View.VISIBLE);
                if (Arrays.binarySearch(MyApplication.getAddFriendRequests(), mUser.getAccount()) != -1) {
                    mAgree.setVisibility(View.VISIBLE);
                    mRefuse.setVisibility(View.VISIBLE);
                    mAddFriend.setVisibility(View.GONE);
                } else {
                    mAgree.setVisibility(View.GONE);
                    mRefuse.setVisibility(View.GONE);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();
        Objects.requireNonNull(getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    private void updateName() {
        new InputDialog(getContext())
                .setTitle("重命名")
                .setHint("请设置您的名称")
                .setOnClickOkListener(new InputDialog.onClickOkListener() {
                    @Override
                    public boolean onClick(InputDialog dialog, String text) {
                        if (text.length() < 1 || text.length() > 6) {
                            dialog.setErrorMessage("长度不符");
                            return false;
                        }
                        final String str = mUser.getName();
                        mUser.setName(text);
                        MyApplication.getServer().updateUser(mUser, new updateUserCallback() {
                            @Override
                            public void onResult(boolean result) {
                                if (result) {
                                    initUserInfo();
                                    MyApplication.getUserManager().updateUser(mUser);
                                } else {
                                    mUser.setName(str);
                                    MyApplication.showTipsDialog("重命名失败");
                                }
                            }
                        });
                        return true;
                    }
                })
                .show();
    }
    private void updateSign() {
        new InputDialog(getContext())
                .setTitle("编辑签名")
                .setHint("请设置您的签名")
                .setOnClickOkListener(new InputDialog.onClickOkListener() {
                    @Override
                    public boolean onClick(InputDialog dialog, String text) {
                        if (text.length() > 20) {
                            dialog.setErrorMessage("不能大于20个字");
                            return false;
                        }
                        final String str = mUser.getSign();
                        mUser.setSign(text);
                        MyApplication.getServer().updateUser(mUser, new updateUserCallback() {
                            @Override
                            public void onResult(boolean result) {
                                if (result) {
                                    initUserInfo();
                                    MyApplication.getUserManager().updateUser(mUser);
                                } else {
                                    mUser.setSign(str);
                                    MyApplication.showTipsDialog("编辑签名失败");
                                }
                            }
                        });
                        return true;
                    }
                })
                .show();
    }
    private void updateSex() {
        final String[] options = new String[] {"男", "女", "保密"};
        new AlertDialog.Builder(MyApplication.getForegroundActivity())
                .setTitle("选择性别")
                .setItems(options, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String str = mUser.getSex();
                        mUser.setSex(options[which]);
                        MyApplication.getServer().updateUser(mUser, new updateUserCallback() {
                            @Override
                            public void onResult(boolean result) {
                                if (result) {
                                    initUserInfo();
                                    MyApplication.getUserManager().updateUser(mUser);
                                } else {
                                    mUser.setSex(str);
                                    MyApplication.showTipsDialog("性别选择失败");
                                }
                            }
                        });
                    }
                })
                .show();
    }
    private void updateOccupation() {
        new InputDialog(getContext())
                .setTitle("编辑职业")
                .setHint("请设置您的职业")
                .setOnClickOkListener(new InputDialog.onClickOkListener() {
                    @Override
                    public boolean onClick(InputDialog dialog, String text) {
                        final String str = mUser.getOccupation();
                        mUser.setOccupation(text);
                        MyApplication.getServer().updateUser(mUser, new updateUserCallback() {
                            @Override
                            public void onResult(boolean result) {
                                if (result) {
                                    initUserInfo();
                                    MyApplication.getUserManager().updateUser(mUser);
                                } else {
                                    mUser.setOccupation(str);
                                    MyApplication.showTipsDialog("编辑职业失败");
                                }
                            }
                        });
                        return true;
                    }
                })
                .show();
    }
    private void updateLocation() {
        new InputDialog(getContext())
                .setTitle("编辑所在地")
                .setHint("请设置您的所在地")
                .setOnClickOkListener(new InputDialog.onClickOkListener() {
                    @Override
                    public boolean onClick(InputDialog dialog, String text) {
                        final String str = mUser.getLocation();
                        mUser.setLocation(text);
                        MyApplication.getServer().updateUser(mUser, new updateUserCallback() {
                            @Override
                            public void onResult(boolean result) {
                                if (result) {
                                    initUserInfo();
                                    MyApplication.getUserManager().updateUser(mUser);
                                } else {
                                    mUser.setLocation(str);
                                    MyApplication.showTipsDialog("编辑所在地失败");
                                }
                            }
                        });
                        return true;
                    }
                })
                .show();
    }
    private void updateHometown() {
        new InputDialog(getContext())
                .setTitle("编辑家乡")
                .setHint("请设置您的家乡")
                .setOnClickOkListener(new InputDialog.onClickOkListener() {
                    @Override
                    public boolean onClick(InputDialog dialog, String text) {
                        final String str = mUser.getHometown();
                        mUser.setHometown(text);
                        MyApplication.getServer().updateUser(mUser, new updateUserCallback() {
                            @Override
                            public void onResult(boolean result) {
                                if (result) {
                                    initUserInfo();
                                    MyApplication.getUserManager().updateUser(mUser);
                                } else {
                                    mUser.setHometown(str);
                                    MyApplication.showTipsDialog("编辑家乡失败");
                                }
                            }
                        });
                        return true;
                    }
                })
                .show();
    }
    private void addFriend() {
        MyApplication.getServer().addFriend(mUser.getAccount(), new requestAddFriendCallback() {
            @Override
            public void onResult(boolean result) {
                MyApplication.showTipsDialog(result ? "成功发送好友请求。" : "发送好友请求失败。");
            }
        });
    }
    private void agreeRequest() {
        MyApplication.getServer().agreeAddFriendRequest(mUser.getAccount(), new agreeAddFriendRequest() {
            @Override
            public void onResult(boolean result) {
                MyApplication.showToast(result ? "已同意" : "同意失败");
                initUserInfo();
            }
        });
    }
    private void refuseRequest() {
        MyApplication.getServer().refuseAddFriendRequest(mUser.getAccount(), new refuseAddFriendRequest() {
            @Override
            public void onResult(boolean result) {
                MyApplication.showToast(result ? "已拒绝" : "拒绝失败");
                initUserInfo();
            }
        });
    }

    @Override
    public void onClick(View v) {
        boolean isMyPersonalInfo = MyApplication.getCurrentUserAccount().equals(mUser.getAccount());
        switch (v.getId()) {
            case R.id.personal_data_head_portrait: {
                if (isMyPersonalInfo) {
                }
                break;
            }
            case R.id.personal_data_name: {
                if (isMyPersonalInfo) {
                    updateName();
                }
                break;
            }
            case R.id.personal_data_sign: {
                if (isMyPersonalInfo) {
                    updateSign();
                }
                break;
            }
            case R.id.personal_data_sex: {
                if (isMyPersonalInfo) {
                    updateSex();
                }
                break;
            }
            case R.id.personal_data_birthday: {
                if (isMyPersonalInfo) {
                }
                break;
            }
            case R.id.personal_data_occupation: {
                if (isMyPersonalInfo) {
                    updateOccupation();
                }
                break;
            }
            case R.id.personal_data_location: {
                if (isMyPersonalInfo) {
                    updateLocation();
                }
                break;
            }
            case R.id.personal_data_hometown: {
                if (isMyPersonalInfo) {
                    updateHometown();
                }
                break;
            }
            case R.id.personal_data_add_friend: {
                addFriend();
                break;
            }
            case R.id.personal_data_agree_request: {
                agreeRequest();
                break;
            }
            case R.id.personal_data_refuse_request: {
                refuseRequest();
                break;
            }
            case R.id.personal_data_close: {
                dismiss();
                break;
            }
        }
    }
}
