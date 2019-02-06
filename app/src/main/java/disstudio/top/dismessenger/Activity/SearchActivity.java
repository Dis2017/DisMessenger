package disstudio.top.dismessenger.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import disstudio.top.dismessenger.Adapter.SearchViewPagerAdapter;
import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.R;

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private EditText mSearchEditText;
    private SearchViewPagerAdapter mSearchViewPagerAdapter;
    private ImageView mIcon;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);

        mViewPager = findViewById(R.id.search_view_pager);
        mTabLayout = findViewById(R.id.search_tab_layout);
        mSearchEditText = findViewById(R.id.search_edit_text);
        mIcon = findViewById(R.id.search_icon);

        mSearchViewPagerAdapter = new SearchViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSearchViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(2);

        mIcon.setOnClickListener(this);
        mSearchEditText.setOnEditorActionListener(this);

        //设置ActionBar
        setTitle(getString(R.string.search));
        Drawable drawable = getResources().getDrawable(R.drawable.arrow_left);
        drawable.setTint(Color.WHITE);
        setLeftButton(drawable, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSoftKeyboard() {
        //隐藏软键盘
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String account = mSearchEditText.getText().toString();
            mSearchViewPagerAdapter.notifySearch(account);
            hideSoftKeyboard();
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_icon: {
                mSearchViewPagerAdapter.notifySearch(mSearchEditText.getText().toString());
                hideSoftKeyboard();
                break;
            }
        }
    }
}
