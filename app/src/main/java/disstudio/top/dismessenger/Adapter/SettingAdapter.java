package disstudio.top.dismessenger.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import disstudio.top.dismessenger.Item.SettingItem;
import disstudio.top.dismessenger.R;
import disstudio.top.dismessenger.ViewHolder.SettingViewHolder;

public class SettingAdapter extends RecyclerView.Adapter<SettingViewHolder> {

    private List<SettingItem> mItems;

    public SettingAdapter(List<SettingItem> items) {
        this.mItems = items;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_setting, viewGroup, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder settingViewHolder, int i) {
        settingViewHolder.bind(mItems.get(i));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
