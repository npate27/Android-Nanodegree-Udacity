package com.neelhpatel.spoileralert.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpirationGroup extends ExpandableGroup<ItemInfo> {

    public ExpirationGroup(String title, List<ItemInfo> items) {
        super(title, items);
    }
}
