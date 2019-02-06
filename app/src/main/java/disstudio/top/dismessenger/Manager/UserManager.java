package disstudio.top.dismessenger.Manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import disstudio.top.dismessenger.Bean.User;
import disstudio.top.dismessenger.Other.MyApplication;

public class UserManager {

    private File getUserFile(String account) {
        File file = new File(Environment.getExternalStorageDirectory() + "/DisStudio/Messenger/User/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, account + ".cache");
    }

    public User readUser(String account) {
        File userFile = getUserFile(account);
        User user = null;
        if (userFile.exists()) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(userFile));
                user = (User)inputStream.readObject();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public void updateUser(User user) {
        File userFile = getUserFile(user.getAccount());
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(userFile));
            outputStream.writeObject(user);
            outputStream.close();
            if (user.getAccount().equals(MyApplication.getCurrentUserAccount())) {
                MyApplication.noticeCurrentUserUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
