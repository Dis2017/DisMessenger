package disstudio.top.dismessenger.Other;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import disstudio.top.dismessenger.Activity.DialogActivity;
import disstudio.top.dismessenger.Activity.MainActivity;
import disstudio.top.dismessenger.Activity.StartActivity;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Manager.UserManager;
import disstudio.top.dismessenger.Network.Server;

public class MyApplication extends Application {

    public static final String CURRENT_USER_ACCOUNT = "currentUserAccount";
    public static final String CURRENT_USER_PASSWORD = "currentUserPassword";

    private UserManager mUserManager;
    private Server mServer;
    private Stack<BaseActivity> mActivities;
    private Map<String, DialogActivity.OnBuildDialogListener> mBuildDailogListenerMap;
    private Map<String, String[]> mAddFriendRequests;

    //提供全局Context
    public static Context getContext() {
        return mInstance.getApplicationContext();
    }
    //获取前景活动
    public static BaseActivity getForegroundActivity() {
        return mInstance.mActivities.peek();
    }
    //提供MainActivity活动实例
    public static MainActivity getMainActivity() {
        for (int i = 0 ; i < mInstance.mActivities.size() ; i++) {
            BaseActivity activity = mInstance.mActivities.get(i);
            if (activity instanceof MainActivity) {
                return (MainActivity)activity;
            }
        }
        return null;
    }
    //在UI线程中运行
    public static void runOnUiThread(Runnable action) {
        getForegroundActivity().runOnUiThread(action);
    }
    //显示Toast
    public static void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //提供Server
    public static Server getServer() {
        return mInstance.mServer;
    }
    //提供当前账号
    public static String getCurrentUserAccount() {
        return getContext().getSharedPreferences("config", MODE_PRIVATE).getString(MyApplication.CURRENT_USER_ACCOUNT, null);
    }
    //提供当前用户密码
    public static String getCurrentUserPassword() {
        return getContext().getSharedPreferences("config", MODE_PRIVATE).getString(MyApplication.CURRENT_USER_PASSWORD, null);
    }
    //提供当前用户
    public static User getCurrentUser() {
        return mInstance.mUserManager.readUser(getCurrentUserAccount());
    }
    //设置当前账号
    public static void setCurrentUser(String account, String password) {
        getContext().getSharedPreferences("config", MODE_PRIVATE).edit().putString(MyApplication.CURRENT_USER_ACCOUNT, account)
        .putString(MyApplication.CURRENT_USER_PASSWORD, password).apply();
        setAddFriendRequests(getAddFriendRequests(""));
        noticeCurrentUserUpdate();
    }
    //提供UserManager
    public static UserManager getUserManager() {
        return mInstance.mUserManager;
    }
    //结束app
    public static void finish() {
        for (BaseActivity activity : mInstance.mActivities) {
            activity.finish();
        }
    }
    //注册Activity
    public static void registerActivity(BaseActivity activity) {
        mInstance.mActivities.push(activity);
    }
    //取消注册Activity
    public static void unRegisterActivity(BaseActivity activity) {
        mInstance.mActivities.remove(activity);
    }
    //通知所有Activity服务器连接
    public static void noticeServerConnect() {
        for (BaseActivity activity : mInstance.mActivities) {
            activity.onServerConnect();
        }
    }
    //通知所有Activity服务器断开连接
    public static void noticeServerDisConnect() {
        finish();
        getContext().startActivity(new Intent(getContext(), StartActivity.class));
    }
    //重连
    public static void reconnectSerer() {
        mInstance.mServer = Server.connect();
    }
    //通知重连失败
    public static void noticeServerConnectFail() {
        for (BaseActivity activity : mInstance.mActivities) {
            activity.onServerConnectFail();
        }
    }
    //通知当前用户信息更新
    public static void noticeCurrentUserUpdate() {
        for (BaseActivity activity : mInstance.mActivities) {
            activity.onCurrentUserUpdate();
        }
    }
    //通知好友请求更新
    public static void noticeAddFriendRequestUpdate() {
        for (BaseActivity activity : mInstance.mActivities) {
            activity.onAddFriendRequestUpdate();
        }
    }
    //显示提示对话框
    public static void showTipsDialog(final String message) {
        showDialog(message, new DialogActivity.OnBuildDialogListener() {
            @Override
            public DialogInterface.OnDismissListener onBuild(AlertDialog.Builder builder) {
                builder.setTitle("提示")
                        .setMessage(message)
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                return null;
            }
        });
    }
    //建立全局对话框构建器
    public static void putOnBuildDialogListener(String key, DialogActivity.OnBuildDialogListener listener) {
        mInstance.mBuildDailogListenerMap.put(key, listener);
    }
    //提供全局对话框构建器
    public static DialogActivity.OnBuildDialogListener getOnBuildDialogListener(String key) {
        return mInstance.mBuildDailogListenerMap.get(key);
    }
    //移除全局对话框构建器
    public static void removeOnBuildDialogListener(String key) {
        mInstance.mBuildDailogListenerMap.remove(key);
    }
    //显示全局对话框
    public static void showDialog(String key) {
        Intent intent = new Intent(getContext(), DialogActivity.class);
        intent.putExtra(DialogActivity.NAME_KEY, key);
        getContext().startActivity(intent);
    }
    public static void showDialog(String key, DialogActivity.OnBuildDialogListener listener) {
        putOnBuildDialogListener(key, listener);
        Intent intent = new Intent(getContext(), DialogActivity.class);
        intent.putExtra(DialogActivity.NAME_KEY, key);
        intent.putExtra(DialogActivity.NAME_CLEAR_CACHE, true);
        getContext().startActivity(intent);
    }
    //提供添加好友请求
    public static String[] getAddFriendRequests() {
        return mInstance.mAddFriendRequests.get(getCurrentUserAccount());
    }
    public static String[] getAddFriendRequests(String account) {
        return mInstance.mAddFriendRequests.get(account);
    }
    //设置好友请求
    public static void setAddFriendRequests(String[] requests) {
        if (mInstance.mAddFriendRequests == null) {
            mInstance.mAddFriendRequests = new HashMap<>();
        }
        String account = getCurrentUserAccount();
        mInstance.mAddFriendRequests.put(account == null ? "" : account, requests);
        noticeAddFriendRequestUpdate();
    }

    private static MyApplication mInstance;
    private void Init() {
        mActivities = new Stack<>();
        mBuildDailogListenerMap = new HashMap<>();
        mUserManager = new UserManager();
        mServer = Server.connect();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Init();
    }
}
