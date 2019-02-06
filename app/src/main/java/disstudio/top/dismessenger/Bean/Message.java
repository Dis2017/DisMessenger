package disstudio.top.dismessenger.Bean;

import java.io.Serializable;

public class Message implements Serializable {

    public Message(int what) {
        this.what = what;
    }

    public static final int WHAT_LOGOUT_RESULT = -2;
    public static final int WHAT_LOGOUT = -1;
    public static final int WHAT_GET_USER = 0;
    public static final int WHAT_GET_USER_RESULT = 1;
    public static final int WHAT_LOGIN = 2;
    public static final int WHAT_LOGIN_RESULT = 3;
    public static final int WHAT_REGISTER = 4;
    public static final int WHAT_REGISTER_RESULT = 5;
    public static final int WHAT_UPDATE_USER = 6;
    public static final int WHAT_UPDATE_USER_RESULT = 7;
    public static final int WHAT_REQUEST_ADD_FRIEND = 8;
    public static final int WHAT_REQUEST_ADD_FRIEND_RESULT = 9;
    public static final int WHAT_HAS_REQUEST_ADD_FRIEND = 10;
    public static final int WHAT_AGREE_ADD_FRIEND_REQUEST = 11;
    public static final int WHAT_AGREE_ADD_FRIEND_REQUEST_RESULT = 12;
    public static final int WHAT_REFUSE_ADD_FRIEND_REQUEST = 13;
    public static final int WHAT_REFUSE_ADD_FRIEND_REQUEST_RESULT = 14;

    public int what;
    public String code;
    public String[] strings;
    public int[] ints;
    public float[] floats;
    public double[] doubles;
    public boolean[] booleans;

}
