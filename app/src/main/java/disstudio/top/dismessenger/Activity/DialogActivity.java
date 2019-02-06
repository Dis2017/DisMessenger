package disstudio.top.dismessenger.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.Other.MyApplication;

public class DialogActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    public static final String NAME_KEY = "DialogActivityKey";
    public static final String NAME_CLEAR_CACHE = "DialogActivityClearCache";

    private DialogInterface.OnDismissListener mOnDismissListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //获取Key
        String key = getIntent().getStringExtra(NAME_KEY);

        if (key != null) {
            //获取OnBuildDialogListener
            OnBuildDialogListener onBuildDialogListener = MyApplication.getOnBuildDialogListener(key);
            if (onBuildDialogListener == null) {
                finish();
                return;
            }
            if (getIntent().getBooleanExtra(NAME_CLEAR_CACHE, false)) {
                MyApplication.removeOnBuildDialogListener(key);
            }
            //Build dialog
            mOnDismissListener = onBuildDialogListener.onBuild(builder);
            //设置dismiss监听
            builder.setOnDismissListener(this);
            //显示
            builder.show();
        } else {
            finish();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
        finish();
    }

    public interface OnBuildDialogListener {
        public DialogInterface.OnDismissListener onBuild(AlertDialog.Builder builder);
    }
}
