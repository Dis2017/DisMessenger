package disstudio.top.dismessenger.Network.Callback;

import com.google.gson.Gson;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Server;

public abstract class loginCallback implements Server.Callback {

    @Override
    public void run(Message msg, boolean result) {
        if (msg != null && msg.what == Message.WHAT_LOGIN_RESULT && msg.booleans != null && msg.strings != null) {
            onResult(msg.booleans[0], new Gson().fromJson(msg.strings[0], User.class));
        } else {
            onResult(false, null);
        }
    }

    public abstract void onResult(boolean result, User user);
}
