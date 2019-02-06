package disstudio.top.dismessenger.Network.Callback;

import disstudio.top.dismessenger.Bean.Message;
import disstudio.top.dismessenger.Network.Server;

public abstract class refuseAddFriendRequest implements Server.Callback {
    @Override
    public void run(Message msg, boolean result) {
        if (result && msg.what == Message.WHAT_REFUSE_ADD_FRIEND_REQUEST_RESULT && msg.booleans != null && msg.booleans.length > 0) {
            onResult(msg.booleans[0]);
        } else {
            onResult(false);
        }
    }

    public abstract void onResult(boolean result) ;
}
