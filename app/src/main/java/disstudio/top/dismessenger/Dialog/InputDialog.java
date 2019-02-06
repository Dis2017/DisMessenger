package disstudio.top.dismessenger.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import disstudio.top.dismessenger.R;

public class InputDialog extends Dialog implements View.OnClickListener {

    private TextView mTitle, mError;
    private EditText mEditText;
    private onClickOkListener mOnClickOkListener;

    public InputDialog(@NonNull Context context) {
        super(context, R.style.InputDialogTheme);

        setContentView(R.layout.dialog_input);

        TextView mOk, mCancel;
        mOk = findViewById(R.id.input_dialog_ok);
        mCancel = findViewById(R.id.input_dialog_cancel);
        mTitle = findViewById(R.id.input_dialog_title);
        mEditText = findViewById(R.id.input_dialog_edit_text);
        mError = findViewById(R.id.input_dialog_error);

        mOk.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public InputDialog setTitle(String title) {
        mTitle.setText(title);
        return this;
    }

    public InputDialog setHint(String hint) {
        mEditText.setHint(hint);
        return this;
    }

    public InputDialog setText(String text) {
        mEditText.setText(text);
        return this;
    }

    public InputDialog setErrorMessage(String msg) {
        mError.setText(msg);
        return this;
    }

    public InputDialog setTextWatcher(TextWatcher watcher) {
        mEditText.addTextChangedListener(watcher);
        return this;
    }

    public InputDialog setOnClickOkListener(onClickOkListener onClickOkListener) {
        mOnClickOkListener = onClickOkListener;
        return this;
    }

    public InputDialog setInputType(int inputType) {
        mEditText.setInputType(inputType);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_dialog_ok: {
                if (mOnClickOkListener == null || mOnClickOkListener.onClick(this, mEditText.getText().toString())) {
                    dismiss();
                }
                break;
            }
            case R.id.input_dialog_cancel: {
                dismiss();
                break;
            }
        }
    }

    public interface onClickOkListener {
        public boolean onClick(InputDialog dialog, String text);
    }
}
