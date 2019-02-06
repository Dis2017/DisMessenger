package disstudio.top.dismessenger.Network.Callback;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Network.Server;

public abstract class logoutCallback implements Server.Callback {

    @Override
    public void run(Message msg, boolean result) {
        if (result) {
            onResult(msg != null && msg.what == Message.WHAT_LOGOUT_RESULT);
        } else {
            onResult(result);
        }
    }

    public abstract void onResult(boolean result);
}
