package disstudio.top.dismessenger.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import disstudio.top.dismessenger.Fragments.DynamicFragment;
import disstudio.top.dismessenger.Fragments.FriendsFragment;
import disstudio.top.dismessenger.Fragments.MessageFragment;
import disstudio.top.dismessenger.Other.BaseActivity;
import disstudio.top.dismessenger.Other.BaseFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragmentList;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new MessageFragment());
        mFragmentList.add(new FriendsFragment());
        mFragmentList.add(new DynamicFragment());
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void onAddFriendRequestUpdate() {
        for (BaseFragment fragment : mFragmentList) {
            if (fragment instanceof FriendsFragment) {
                ((FriendsFragment) fragment).updateView();
            }
        }
    }
}
