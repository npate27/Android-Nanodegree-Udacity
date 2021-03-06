package com.neelhpatel.spoileralert.ui.nav;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.databinding.ItemHeaderBinding;
import com.xwray.groupie.databinding.BindableItem;

public class HeaderItem extends BindableItem<ItemHeaderBinding> {

    @StringRes private int titleStringResId;
    @StringRes private int subtitleResId;
    @DrawableRes private int iconResId;
    private View.OnClickListener onIconClickListener;

    public HeaderItem(@StringRes int titleStringResId) {
        this(titleStringResId, 0);
    }

    public HeaderItem(@StringRes int titleStringResId, @StringRes int subtitleResId) {
        this(titleStringResId, subtitleResId, 0, null);
    }

    public HeaderItem(@StringRes int titleStringResId, @StringRes int subtitleResId, @DrawableRes int iconResId, View.OnClickListener onIconClickListener) {
        this.titleStringResId = titleStringResId;
        this.subtitleResId = subtitleResId;
        this.iconResId = iconResId;
        this.onIconClickListener = onIconClickListener;
    }

    @Override public int getLayout() {
        return R.layout.item_header;
    }

    @Override public void bind(@NonNull ItemHeaderBinding viewBinding, int position) {
        viewBinding.title.setText(titleStringResId);
        if (subtitleResId > 0) {
            viewBinding.subtitle.setText(subtitleResId);
        }
        viewBinding.subtitle.setVisibility(subtitleResId > 0 ? View.VISIBLE : View.GONE);

        if (iconResId > 0) {
            viewBinding.icon.setImageResource(iconResId);
            viewBinding.icon.setOnClickListener(onIconClickListener);
        }
        viewBinding.icon.setVisibility(iconResId > 0 ? View.VISIBLE : View.GONE);
    }
}