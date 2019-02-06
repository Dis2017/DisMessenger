package disstudio.top.dismessenger.Network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Other.MyApplication;

public class Server extends Thread {

    public static Server connect() {
        if (mInstance == null) {
            mInstance = new Server();
            mInstance.start();
        }
        return mInstance;
    }

    private static Server mInstance;

    private Socket mSocket;
    private BufferedWriter mBufferedWriter;
    private Gson mGson;
    private Map<String, Callback> mCallbackMap;

    //退出登录
    public void logout(Callback callback) {
        Message msg = new Message(Message.WHAT_LOGOUT);
        sendMessage(msg, callback);
    }
    //从服务器获取用户实例
    public void getUserByAccount(String account[], Callback callback) {
        Message msg = new Message(Message.WHAT_GET_USER);
        msg.strings = account;
        sendMessage(msg, callback);
    }
    //登录
    public void login(String account, String password, Callback callback) {
        Message msg = new Message(Message.WHAT_LOGIN);
        msg.strings = new String[] {account, password};
        sendMessage(msg, callback);
    }
    //注册
    public void register(String password, Callback callback) {
        Message msg = new Message(Message.WHAT_REGISTER);
        msg.strings = new String[] {password};
        sendMessage(msg, callback);
    }
    //更新用户
    public void updateUser(User user, Callback callback) {
        Message msg = new Message(Message.WHAT_UPDATE_USER);
        msg.strings = new String[]{mGson.toJson(user)};
        sendMessage(msg, callback);
    }
    //添加好友
    public void addFriend(String account, Callback callback) {
        Message msg = new Message(Message.WHAT_REQUEST_ADD_FRIEND);
        msg.strings = new String[] {account};
        sendMessage(msg, callback);
    }
    //拒绝添加好友
    public void refuseAddFriendRequest(String account, Callback callback) {
        Message msg = new Message(Message.WHAT_REFUSE_ADD_FRIEND_REQUEST);
        msg.strings = new String[] {account};
        sendMessage(msg , callback);
    }
    //同意添加好友
    public void agreeAddFriendRequest(String account, Callback callback) {
        Message msg = new Message(Message.WHAT_AGREE_ADD_FRIEND_REQUEST);
        msg.strings = new String[] {account};
        sendMessage(msg , callback);
    }

    //处理收到到消息
    private void handleMessage(final Message msg) {
        new Thread() {
            @Override
            public void run() {
                Callback callback = mCallbackMap.get(msg.code);
                mCallbackMap.remove(msg.code);
                if (callback != null) {
                    callback.run(msg, true);
                }
            }
        }.start();
        switch (msg.what) {
            case Message.WHAT_HAS_REQUEST_ADD_FRIEND: {
                MyApplication.setAddFriendRequests(msg.strings);
                break;
            }
        }
    }
    //发消息
    private void sendMessage(final Message msg, final Callback callback) {
        if (mBufferedWriter != null) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    msg.code = msg.toString();
                    String json = mGson.toJson(msg);
                    try {
                        mBufferedWriter.write(json + "\n");
                        mBufferedWriter.flush();
                        mCallbackMap.put(msg.code, callback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            callback.run(null, false);
        }
    }
    public boolean isConnect() {
        return mSocket != null && mBufferedWriter != null;
    }

    @Override
    public void run() {
        super.run();
        try {
            BufferedReader mBufferedReader;
            mSocket = new Socket("127.0.0.1", 40626);
            mBufferedWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
            mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mGson = new Gson();
            mCallbackMap = new HashMap<>();
            //通知服务器连接
            MyApplication.noticeServerConnect();
            //开始监听
            while (!mSocket.isClosed()) {
                String json = mBufferedReader.readLine();
                if (json != null) {
                    final Message msg = mGson.fromJson(json, Message.class);
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            handleMessage(msg);
                        }
                    }.start();
                } else {
                    MyApplication.showToast("与服务器断开连接。");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            mSocket = null;
            mBufferedWriter = null;
            mInstance = null;
            //通知服务器链接失败
            MyApplication.noticeServerConnectFail();
            return;
        }
        mSocket = null;
        mBufferedWriter = null;
        mInstance = null;
        //通知服务器断开连接
        MyApplication.noticeServerDisConnect();
    }

    public interface Callback {
        public void run(Message msg, boolean result);
    }
}
