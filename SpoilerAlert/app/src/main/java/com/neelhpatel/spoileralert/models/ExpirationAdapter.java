package com.neelhpatel.spoileralert.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.spoileralert.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpirationAdapter extends ExpandableRecyclerViewAdapter<ExpirationViewHolder, ItemViewHolder> {

    public ExpirationAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ExpirationViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expiration, parent, false);
        return new ExpirationViewHolder(view);
        }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_food, parent, false);
        return new ItemViewHolder(view.getContext(), view);
    }

    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ItemInfo itemInfo= ((ExpirationGroup) group).getItems().get(childIndex);
        holder.onBind(itemInfo);
    }

    @Override
    public void onBindGroupViewHolder(ExpirationViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setExpirationTitle(group.getTitle());
    }
}
