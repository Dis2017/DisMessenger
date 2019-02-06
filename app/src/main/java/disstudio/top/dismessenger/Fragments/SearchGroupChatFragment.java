package disstudio.top.dismessenger.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import disstudio.top.dismessenger.Adapter.SearchViewPagerAdapter;
import disstudio.top.dismessenger.R;

public class SearchGroupChatFragment extends SearchViewPagerAdapter.SearchFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_group_chat, container, false);
        return view;
    }

    @Override
    public void onSearch(String account) {

    }
}
