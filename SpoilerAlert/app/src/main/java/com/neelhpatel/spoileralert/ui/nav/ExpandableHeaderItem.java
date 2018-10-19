package com.neelhpatel.spoileralert.ui.nav;

import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.databinding.ItemHeaderBinding;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;

public class ExpandableHeaderItem extends HeaderItem implements ExpandableItem {

    private ExpandableGroup expandableGroup;

    public ExpandableHeaderItem(@StringRes int titleStringResId, @StringRes int subtitleResId) {
        super(titleStringResId, subtitleResId);
    }

    @Override public void bind(@NonNull final ItemHeaderBinding viewBinding, int position) {
        super.bind(viewBinding, position);

        // Initial icon state -- not animated.
        viewBinding.icon.setVisibility(View.VISIBLE);
        viewBinding.icon.setImageResource(expandableGroup.isExpanded() ? R.drawable.ic_arrow_drop_down_black_24dp : R.drawable.ic_arrow_drop_down_black_24dp);
        viewBinding.icon.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                expandableGroup.onToggleExpanded();
                bindIcon(viewBinding);
            }
        });
    }

    private void bindIcon(ItemHeaderBinding viewBinding) {
        viewBinding.icon.setVisibility(View.VISIBLE);
        viewBinding.icon.setImageResource(expandableGroup.isExpanded() ? R.drawable.ic_arrow_drop_down_black_24dp : R.drawable.ic_arrow_drop_down_black_24dp);
    }

    @Override public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
        this.expandableGroup = onToggleListener;
    }
}