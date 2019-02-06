package disstudio.top.dismessenger.Network.Callback;

import com.google.gson.Gson;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Server;

public abstract class registerCallback implements Server.Callback {
    @Override
    public void run(Message msg, boolean result) {
        if (msg != null && msg.what == Message.WHAT_REGISTER_RESULT) {
            User user =new Gson().fromJson(msg.strings[0], User.class);
            onResult(true, user);
        } else {
            onResult(false, null);
        }
    }

    public abstract void onResult(boolean result, User user);
}
