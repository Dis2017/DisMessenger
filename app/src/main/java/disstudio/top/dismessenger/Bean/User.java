package disstudio.top.dismessenger.Bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class User implements Serializable {

    private String name;
    private String account;
    private String sign;
    private String sex;
    private long birthday;
    private String occupation;
    private String location;
    private String hometown;
    private byte[] headPortraitSrc;
    private transient Bitmap headPortrait;
    private String[] friendsAccount;

    public User(String account) {
        this.name = "Dis用户";
        this.account = account;
        this.sex = "保密";
        this.sign = "";
        this.birthday = System.currentTimeMillis();
        this.occupation = "";
        this.location = "";
        this.hometown = "";
        setHeadPortrait(((BitmapDrawable)MyApplication.getContext().getResources().getDrawable(R.drawable.def_user_head_portrait)).getBitmap());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Bitmap getHeadPortrait() {
        if (headPortrait == null) {

            if (headPortraitSrc != null) {
                headPortrait = BitmapFactory.decodeByteArray(headPortraitSrc, 0, headPortraitSrc.length);
            } else {
                //提供默认头像
                setHeadPortrait(((BitmapDrawable) MyApplication.getContext().getResources().getDrawable(R.drawable.def_user_head_portrait)).getBitmap());
            }

        }
        return headPortrait;
    }

    public void setHeadPortrait(Bitmap headPortrait) {
        if (headPortrait == null) {
            return;
        }
        this.headPortrait = headPortrait;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        headPortrait.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        headPortraitSrc = outputStream.toByteArray();
    }

    public String getSign() {
        return "".equals(sign) ? MyApplication.getContext().getResources().getString(R.string.default_sign) : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSex() {
        return "".equals(sex) ? "？？（点击设置）" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        Date now = new Date(System.currentTimeMillis());
        return  (now.getYear() - new Date(birthday).getYear()) + "";
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        Date date = new Date(birthday);
        switch (date.getMonth() + 1) {
            case 1: {
                if (date.getDay() >= 20) {
                    return "水瓶座";
                } else {
                    return "摩羯座";
                }
            }
            case 2: {
                if (date.getDay() >= 19) {
                    return "双鱼座";
                } else {
                    return "水瓶座";
                }
            }
            case 3: {
                if (date.getDay() >= 21) {
                    return "白羊座";
                } else {
                    return "双鱼座";
                }
            }
            case 4: {
                if (date.getDay() >= 21) {
                    return "金牛座";
                } else {
                    return "白羊座";
                }
            }
            case 5: {
                if (date.getDay() >= 21) {
                    return "双子座";
                } else {
                    return "金牛座";
                }
            }
            case 6: {
                if (date.getDay() >= 22) {
                    return "巨蟹座";
                } else {
                    return "双子座";
                }
            }
            case 7: {
                if (date.getDay() >= 23) {
                    return "狮子座";
                } else {
                    return "巨蟹座";
                }
            }
            case 8: {
                if (date.getDay() >= 23) {
                    return "处女座";
                } else {
                    return "狮子座";
                }
            }
            case 9: {
                if (date.getDay() >= 23) {
                    return "天秤座";
                } else {
                    return "处女座";
                }
            }
            case 10: {
                if (date.getDay() >= 24) {
                    return "天蝎座";
                } else {
                    return "天秤座";
                }
            }
            case 11: {
                if (date.getDay() >= 23) {
                    return "射手座";
                } else {
                    return "天蝎座";
                }
            }
            case 12: {
                if (date.getDay() >= 22) {
                    return "摩羯座";
                } else {
                    return "射手座";
                }
            }
        }
        return "";
    }

    public String getOccupation() {
        return "".equals(occupation) ? "点击输入" : occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLocation() {
        return "".equals(location) ? "点击输入" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHometown() {
        return "".equals(hometown) ? "点击输入" : hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String[] getFriendsAccount() {
        return friendsAccount;
    }

    public void setFriendsAccount(String[] friendsAccount) {
        this.friendsAccount = friendsAccount;
    }
}
