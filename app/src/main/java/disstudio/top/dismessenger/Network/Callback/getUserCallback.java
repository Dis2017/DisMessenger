package disstudio.top.dismessenger.Network.Callback;

import com.google.gson.Gson;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Network.Server;

public abstract class getUserCallback implements Server.Callback {
    @Override
    public void run(Message msg, boolean result) {
        if (msg != null && msg.what == Message.WHAT_GET_USER_RESULT && msg.strings != null) {
            Gson gson = new Gson();
            User[] users = new User[msg.strings.length];
            int i = 0;
            for (String json : msg.strings) {
                if (json != null) {
                    users[i] = gson.fromJson(json, User.class);
                    i++;
                }
            }
            onResult(true, users);
        } else {
            onResult(false, null);
        }
    }

    public abstract void onResult(boolean result, User[] users);
}
