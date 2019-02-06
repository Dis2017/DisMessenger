package disstudio.top.dismessenger.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import disstudio.top.dismessenger.Fragments.SearchFindFragment;
import disstudio.top.dismessenger.Fragments.SearchFriendFragment;
import disstudio.top.dismessenger.Fragments.SearchGroupChatFragment;
import disstudio.top.dismessenger.Other.BaseFragment;
import disstudio.top.dismessenger.Other.MyApplication;
import disstudio.top.dismessenger.R;

public class SearchViewPagerAdapter extends FragmentPagerAdapter {

    private List<SearchFragment> mFragments;

    public SearchViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments =new ArrayList<>();
        mFragments.add(new SearchFriendFragment());
        mFragments.add(new SearchGroupChatFragment());
        mFragments.add(new SearchFindFragment());
    }

    public void notifySearch(String account) {
        for (SearchFragment fragment : mFragments) {
            fragment.onSearch(account);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        BaseFragment fragment = mFragments.get(position);
        if (fragment != null) {
            if (fragment instanceof SearchFriendFragment) {
                return MyApplication.getContext().getString(R.string.friends);
            } else if (fragment instanceof SearchGroupChatFragment) {
                return MyApplication.getContext().getString(R.string.group_chat);
            } else if (fragment instanceof SearchFindFragment) {
                return MyApplication.getContext().getString(R.string.find);
            }
        }
        return super.getPageTitle(position);
    }

    @Override
    public BaseFragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public static abstract class SearchFragment extends BaseFragment {
        protected abstract void onSearch(String account);
    }
}
