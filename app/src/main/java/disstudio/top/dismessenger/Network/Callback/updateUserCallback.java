package disstudio.top.dismessenger.Network.Callback;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Network.Server;

public abstract class updateUserCallback implements Server.Callback {

    @Override
    public void run(Message msg, boolean result) {
        if (msg != null && msg.what == Message.WHAT_UPDATE_USER_RESULT) {
            onResult(msg.booleans[0]);
        } else {
            onResult(false);
        }
    }

    public abstract void onResult(boolean result);
}
