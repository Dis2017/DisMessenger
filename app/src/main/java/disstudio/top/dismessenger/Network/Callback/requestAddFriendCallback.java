package disstudio.top.dismessenger.Network.Callback;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Network.Server;

public abstract class requestAddFriendCallback implements Server.Callback {

    @Override
    public void run(Message msg, boolean result) {
        if (msg != null && msg.what == Message.WHAT_REQUEST_ADD_FRIEND_RESULT && msg.booleans != null) {
            onResult(msg.booleans[0]);
        } else {
            onResult(false);
        }
    }

    public abstract void onResult(boolean result);
}
